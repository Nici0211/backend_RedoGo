package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.MenuCardDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

/**
 * White-Box-Tests für {@link MenuCardService#getMenuCard(Long)} (US28).
 *
 * <p>Die drei Repositories sind Mockito-Mocks. Dadurch kann jeder Zweig der Methode gezielt
 * erzwungen und geprüft werden, welche Repository-Methoden aufgerufen werden:
 *
 * <ol>
 *   <li>restaurantRepo.findById(id) -> leer: 404 (Ablauf endet sofort)
 *   <li>dishRepo.findByRestaurantIdOrderByIdAsc(id) -> Gerichte werden auf MenuCardEntryDto gemappt
 *   <li>menuRepo.findByRestaurantIdOrderByIdAsc(id) -> Menüs werden auf MenuCardEntryDto gemappt
 *   <li>new MenuCardDto(restaurantId, restaurantName, gerichte, menues)
 * </ol>
 */
@ContextConfiguration(classes = {MenuCardService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class MenuCardServiceDiffblueTest {

    @Autowired
    private MenuCardService menuCardService;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @MockitoBean
    private DishRepository dishRepository;

    @MockitoBean
    private MenuRepository menuRepository;

    // ═════════════════════════════ A) Restaurant nicht gefunden ═════════════════════════════

    /**
     * Was passiert: restaurantRepo.findById(1) liefert Optional.empty().
     * Erwartet: Es wird eine ResponseStatusException mit Status 404 (NOT_FOUND) geworfen.
     */
    @Test
    @DisplayName("getMenuCard(Long); Restaurant nicht vorhanden -> 404 NOT_FOUND")
    void testGetMenuCard_restaurantNotFound_throws404() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(restaurantRepository).findById(1L);
    }

    /**
     * Was passiert: Restaurant mit ID 42 existiert nicht.
     * Erwartet: Die Fehlermeldung lautet genau "Restaurant mit der ID 42 wurde nicht gefunden",
     * d.h. die angefragte ID wird in den Text eingebaut.
     */
    @Test
    @DisplayName("getMenuCard(Long); Fehlermeldung enthält die angefragte ID")
    void testGetMenuCard_restaurantNotFound_messageContainsId() {
        // Arrange
        when(restaurantRepository.findById(42L)).thenReturn(Optional.empty());

        // Act
        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(42L));

        // Assert
        assertEquals("Restaurant mit der ID 42 wurde nicht gefunden", ex.getReason());
    }

    /**
     * Was passiert: Restaurant nicht vorhanden -> Exception.
     * Erwartet: dishRepo und menuRepo werden NIE aufgerufen (der Ablauf endet vor dem Laden
     * der Gerichte und Menüs, es werden keine unnötigen Datenbankabfragen gemacht).
     */
    @Test
    @DisplayName("getMenuCard(Long); Restaurant nicht vorhanden -> Gerichte/Menüs werden nicht geladen")
    void testGetMenuCard_restaurantNotFound_doesNotLoadDishesOrMenus() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));

        // Assert
        verifyNoInteractions(dishRepository);
        verifyNoInteractions(menuRepository);
    }

    // ═════════════════════════════ B) Restaurant gefunden: Mapping ═════════════════════════════

    /**
     * Was passiert: Restaurant 1 "RedoGo Linz" existiert, hat aber keine Gerichte und keine Menüs
     * (beide Repos liefern eine leere Liste).
     * Erwartet: Kein Fehler; restaurantId = 1, restaurantName = "RedoGo Linz",
     * "gerichte" und "menues" sind leer.
     */
    @Test
    @DisplayName("getMenuCard(Long); Restaurant ohne Einträge -> leere Listen")
    void testGetMenuCard_restaurantWithoutEntries_returnsEmptyLists() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(1L, result.getRestaurantId().longValue());
        assertEquals("RedoGo Linz", result.getRestaurantName());
        assertTrue(result.getGerichte().isEmpty());
        assertTrue(result.getMenues().isEmpty());
    }

    /**
     * Was passiert: Ein Gericht (ID 10, "Classic Cheeseburger", Beschreibung, 8.90, verfügbar)
     * wird vom dishRepo geliefert.
     * Erwartet: Im Ergebnis steht genau 1 Gericht, und alle 5 Felder (id, name, description, price,
     * available) wurden unverändert aus der Entity übernommen.
     */
    @Test
    @DisplayName("getMenuCard(Long); Gericht wird mit allen Feldern gemappt")
    void testGetMenuCard_dishIsMappedWithAllFields() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(dish(10L, "Classic Cheeseburger", "Rindfleisch und Cheddar", "8.90", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(1, result.getGerichte().size());
        MenuCardEntryDto entry = result.getGerichte().get(0);
        assertEquals(10L, entry.getId().longValue());
        assertEquals("Classic Cheeseburger", entry.getName());
        assertEquals("Rindfleisch und Cheddar", entry.getDescription());
        assertEquals(new BigDecimal("8.90"), entry.getPrice());
        assertTrue(entry.isAvailable());
    }

    /**
     * Was passiert: Ein Menü (ID 20, "Burger-Menü", Beschreibung, 11.90, verfügbar) wird vom
     * menuRepo geliefert.
     * Erwartet: Im Ergebnis steht genau 1 Menü, und alle 5 Felder wurden unverändert übernommen.
     */
    @Test
    @DisplayName("getMenuCard(Long); Menü wird mit allen Feldern gemappt")
    void testGetMenuCard_menuIsMappedWithAllFields() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(menu(20L, "Burger-Menü", "Burger, Pommes und Getränk", "11.90", true)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(1, result.getMenues().size());
        MenuCardEntryDto entry = result.getMenues().get(0);
        assertEquals(20L, entry.getId().longValue());
        assertEquals("Burger-Menü", entry.getName());
        assertEquals("Burger, Pommes und Getränk", entry.getDescription());
        assertEquals(new BigDecimal("11.90"), entry.getPrice());
        assertTrue(entry.isAvailable());
    }

    /**
     * Was passiert: Ein Gericht mit available = false wird geliefert.
     * Erwartet: Das Flag bleibt false (es wird nicht auf true gesetzt oder weggefiltert);
     * das Gericht ist trotzdem in der Liste (nicht verfügbare Gerichte werden mit angezeigt).
     */
    @Test
    @DisplayName("getMenuCard(Long); nicht verfügbares Gericht bleibt in der Liste mit available=false")
    void testGetMenuCard_unavailableDish_keptWithFlagFalse() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(dish(10L, "Ausverkauft", "Heute nicht da", "5.00", false)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(1, result.getGerichte().size());
        assertFalse(result.getGerichte().get(0).isAvailable());
    }

    /**
     * Was passiert: Ein Menü mit available = false wird geliefert.
     * Erwartet: Das Menü bleibt in der Liste und das Flag bleibt false.
     */
    @Test
    @DisplayName("getMenuCard(Long); nicht verfügbares Menü bleibt in der Liste mit available=false")
    void testGetMenuCard_unavailableMenu_keptWithFlagFalse() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(menu(20L, "Aktionsmenü", "Nur zeitweise", "9.90", false)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(1, result.getMenues().size());
        assertFalse(result.getMenues().get(0).isAvailable());
    }

    /**
     * Was passiert: Das dishRepo liefert die Gerichte in der Reihenfolge ID 1, 2, 3
     * (das Repository sortiert "OrderByIdAsc").
     * Erwartet: Das Mapping (stream().map().toList()) ändert die Reihenfolge nicht:
     * Ergebnis ebenfalls ID 1, 2, 3.
     */
    @Test
    @DisplayName("getMenuCard(Long); Reihenfolge der Gerichte aus dem Repository bleibt erhalten")
    void testGetMenuCard_dishOrderIsPreserved() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(
                        dish(1L, "Erstes", "a", "1.00", true),
                        dish(2L, "Zweites", "b", "2.00", true),
                        dish(3L, "Drittes", "c", "3.00", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(List.of(1L, 2L, 3L), result.getGerichte().stream().map(MenuCardEntryDto::getId).toList());
    }

    /**
     * Was passiert: Das menuRepo liefert die Menüs in der Reihenfolge ID 5, 6.
     * Erwartet: Ergebnis ebenfalls in der Reihenfolge 5, 6.
     */
    @Test
    @DisplayName("getMenuCard(Long); Reihenfolge der Menüs aus dem Repository bleibt erhalten")
    void testGetMenuCard_menuOrderIsPreserved() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(
                        menu(5L, "Steirer-Menü", "a", "13.50", true),
                        menu(6L, "Kernöl-Menü", "b", "12.90", true)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(List.of(5L, 6L), result.getMenues().stream().map(MenuCardEntryDto::getId).toList());
    }

    /**
     * Was passiert: Gerichte und Menüs haben bewusst die gleiche ID (7), aber unterschiedliche Namen.
     * Erwartet: Gerichte landen NUR in "gerichte", Menüs NUR in "menues" (die beiden Listen
     * werden nicht vertauscht oder vermischt).
     */
    @Test
    @DisplayName("getMenuCard(Long); Gerichte und Menüs landen in den richtigen Listen")
    void testGetMenuCard_dishesAndMenusAreNotMixedUp() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(dish(7L, "Ein Gericht", "d", "4.00", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(menu(7L, "Ein Menü", "m", "9.00", true)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals("Ein Gericht", result.getGerichte().get(0).getName());
        assertEquals("Ein Menü", result.getMenues().get(0).getName());
    }

    /**
     * Was passiert: Ein Gericht hat keine Beschreibung (description = null).
     * Erwartet: Kein NullPointerException; die Beschreibung ist im Ergebnis ebenfalls null,
     * alle anderen Felder sind normal gesetzt.
     */
    @Test
    @DisplayName("getMenuCard(Long); Gericht ohne Beschreibung -> description bleibt null, kein Fehler")
    void testGetMenuCard_dishWithoutDescription_keepsNull() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(dish(10L, "Pommes Frites", null, "3.50", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals("Pommes Frites", result.getGerichte().get(0).getName());
        assertNull(result.getGerichte().get(0).getDescription());
    }

    // ═════════════════════════════ C) Aufrufe der Repositories ═════════════════════════════

    /**
     * Was passiert: getMenuCard(7) wird aufgerufen.
     * Erwartet: Alle drei Repositories werden genau EINMAL mit der ID 7 aufgerufen
     * (findById, findByRestaurantIdOrderByIdAsc je für Gerichte und Menüs) und es gibt keine
     * weiteren Aufrufe, insbesondere kein save/delete (die Methode ist nur lesend).
     */
    @Test
    @DisplayName("getMenuCard(Long); jedes Repository wird genau einmal mit der ID aufgerufen")
    void testGetMenuCard_callsEachRepositoryExactlyOnce() {
        // Arrange
        when(restaurantRepository.findById(7L)).thenReturn(Optional.of(restaurant(7L, "RedoGo Graz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(7L)).thenReturn(List.of());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(7L)).thenReturn(List.of());

        // Act
        menuCardService.getMenuCard(7L);

        // Assert
        verify(restaurantRepository).findById(7L);
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(7L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(7L);
        verifyNoMoreInteractions(restaurantRepository, dishRepository, menuRepository);
    }

    /**
     * Was passiert: dishRepo wirft eine RuntimeException (z.B. Datenbank nicht erreichbar).
     * Erwartet: Die Exception wird nicht verschluckt, sondern nach oben weitergegeben;
     * das menuRepo wird danach nicht mehr aufgerufen (Reihenfolge: erst Gerichte, dann Menüs).
     */
    @Test
    @DisplayName("getMenuCard(Long); Fehler beim Laden der Gerichte wird weitergegeben")
    void testGetMenuCard_dishRepositoryFails_exceptionPropagates() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L)).thenThrow(new IllegalStateException("DB down"));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> menuCardService.getMenuCard(1L));
        verifyNoInteractions(menuRepository);
    }

    // ═════════════════════════════ D) Logik: Rechnen mit den Ergebnissen ═════════════════════════════

    /**
     * Rechnung Anzahl: 2 Gerichte + 1 Menü = 3 Einträge.
     * Was passiert: Repos liefern 2 Gerichte und 1 Menü.
     * Erwartet: gerichte.size() = 2, menues.size() = 1, Summe = 3 (es geht nichts verloren
     * und es kommt nichts dazu).
     */
    @Test
    @DisplayName("getMenuCard(Long); Anzahl: 2 Gerichte + 1 Menü = 3 Einträge")
    void testGetMenuCard_countOfEntries() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(
                        dish(1L, "Classic Cheeseburger", "a", "8.90", true),
                        dish(2L, "Pommes Frites", "b", "3.50", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(menu(3L, "Burger-Menü", "c", "11.90", true)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(2, result.getGerichte().size());
        assertEquals(1, result.getMenues().size());
        assertEquals(3, result.getGerichte().size() + result.getMenues().size());
    }

    /**
     * Rechnung Summe: 8.90 + 3.50 = 12.40.
     * Was passiert: Die Preise der gemappten Gerichte werden addiert.
     * Erwartet: Summe = 12.40 (die Preise wurden beim Mappen nicht gerundet oder verändert).
     */
    @Test
    @DisplayName("getMenuCard(Long); Summe der Gerichtspreise 8.90 + 3.50 = 12.40")
    void testGetMenuCard_sumOfDishPrices() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(
                        dish(1L, "Classic Cheeseburger", "a", "8.90", true),
                        dish(2L, "Pommes Frites", "b", "3.50", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        BigDecimal sum = result.getGerichte().stream()
                .map(MenuCardEntryDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(0, new BigDecimal("12.40").compareTo(sum));
    }

    /**
     * Rechnung Menü-Ersparnis: Cheeseburger 8.90 + Pommes 3.50 = 12.40 einzeln,
     * das Burger-Menü kostet 11.90 -> Ersparnis = 12.40 - 11.90 = 0.50.
     * Was passiert: Menüpreis und Summe der Einzelpreise aus dem Ergebnis werden verglichen.
     * Erwartet: Das Menü ist günstiger als die Einzelpreise, die Ersparnis beträgt 0.50.
     */
    @Test
    @DisplayName("getMenuCard(Long); Menü 11.90 ist um 0.50 günstiger als 8.90 + 3.50")
    void testGetMenuCard_menuIsCheaperThanSingleDishes() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(
                        dish(1L, "Classic Cheeseburger", "a", "8.90", true),
                        dish(2L, "Pommes Frites", "b", "3.50", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(menu(3L, "Burger-Menü", "c", "11.90", true)));

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        BigDecimal singleSum = result.getGerichte().stream()
                .map(MenuCardEntryDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal menuPrice = result.getMenues().get(0).getPrice();
        assertTrue(menuPrice.compareTo(singleSum) < 0);
        assertEquals(0, new BigDecimal("0.50").compareTo(singleSum.subtract(menuPrice)));
    }

    /**
     * Rechnung Preis unverändert: 8.90 bleibt 8.90 (nicht 8.9 und nicht 9).
     * Was passiert: Ein Preis mit 2 Nachkommastellen wird gemappt.
     * Erwartet: BigDecimal.equals ist true (gleicher Wert UND gleiche Skalierung 2),
     * d.h. der Preis wird 1:1 durchgereicht, ohne Rundung oder Umrechnung.
     */
    @Test
    @DisplayName("getMenuCard(Long); Preis 8.90 wird ohne Rundung übernommen")
    void testGetMenuCard_priceIsNotRounded() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant(1L, "RedoGo Linz")));
        when(dishRepository.findByRestaurantIdOrderByIdAsc(1L))
                .thenReturn(List.of(dish(1L, "Classic Cheeseburger", "a", "8.90", true)));
        when(menuRepository.findByRestaurantIdOrderByIdAsc(1L)).thenReturn(List.of());

        // Act
        MenuCardDto result = menuCardService.getMenuCard(1L);

        // Assert
        assertEquals(new BigDecimal("8.90"), result.getGerichte().get(0).getPrice());
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    private Restaurant restaurant(Long id, String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        return restaurant;
    }

    private Dish dish(Long id, String name, String description, String price, boolean available) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(new BigDecimal(price));
        dish.setAvailable(available);
        return dish;
    }

    private Menu menu(Long id, String name, String description, String price, boolean available) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(new BigDecimal(price));
        menu.setAvailable(available);
        return menu;
    }
}