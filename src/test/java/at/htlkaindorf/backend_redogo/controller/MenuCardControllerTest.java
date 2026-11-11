package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US28: Als Kunde möchte ich die Speisekarte von jedem Restaurant sehen.
 * Black-Box-Tests: geprüft wird nur der HTTP-Aufruf GET /restaurant/{id}/speisekarte.
 *
 * Testdaten (werden vor jedem Test neu angelegt):
 *   Linz : Gerichte  Classic Cheeseburger 8.90, Pommes Frites 3.50
 *          Menüs     Burger-Menü 11.90
 *   Graz : Gerichte  Kürbiskernöl-Burger 9.50
 *          Menüs     Steirer-Menü 13.50, Kernöl-Menü 12.90
 *   Wien : keine Gerichte, keine Menüs
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuCardControllerTest {

    private static final String LINZ_NAME = "RedoGo Linz Landstraße";
    private static final String GRAZ_NAME = "RedoGo Graz Hauptplatz";
    private static final String WIEN_NAME = "RedoGo Wien Naschmarkt";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private DishRepository dishRepo;

    @Autowired
    private MenuRepository menuRepo;

    private Restaurant linz;
    private Restaurant graz;
    private Restaurant wien;

    @BeforeEach
    void setUpMockData() {
        linz = saveRestaurant(LINZ_NAME);
        graz = saveRestaurant(GRAZ_NAME);
        wien = saveRestaurant(WIEN_NAME);

        // Linz: 2 Gerichte + 1 Menü
        saveDish(linz, "Classic Cheeseburger", "Rindfleisch, Cheddar und Salat", "8.90");
        saveDish(linz, "Pommes Frites", "Knusprig mit Meersalz", "3.50");
        saveMenu(linz, "Burger-Menü", "Cheeseburger, Pommes und Getränk", "11.90");

        // Graz: 1 Gericht + 2 Menüs
        saveDish(graz, "Kürbiskernöl-Burger", "Mit Rucola und Ziegenkäse", "9.50");
        saveMenu(graz, "Steirer-Menü", "Käferbohnensuppe und Grammelknödel", "13.50");
        saveMenu(graz, "Kernöl-Menü", "Burger mit Beilagensalat und Getränk", "12.90");
    }

    // ═════════════════════════════ A) Speisekarte anzeigen ═════════════════════════════

    /**
     * Was passiert: GET /restaurant/{linzId}/speisekarte wird aufgerufen.
     * Erwartet: Status 200, Content-Type JSON, und die Antwort enthält die beiden
     * Listen "gerichte" und "menues" (jeweils als Array).
     */
    @Test
    void menuCard_respondsWith200AndJson() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gerichte").isArray())
                .andExpect(jsonPath("$.menues").isArray());
    }

    /**
     * Was passiert: Speisekarte von Linz wird abgerufen.
     * Erwartet: "restaurantId" ist die ID von Linz und "restaurantName" ist
     * "RedoGo Linz Landstraße" -> die Speisekarte ist eindeutig einem Restaurant zugeordnet.
     */
    @Test
    void menuCard_containsRestaurantIdAndName() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(linz.getId()))
                .andExpect(jsonPath("$.restaurantName").value(LINZ_NAME));
    }

    /**
     * Was passiert: Speisekarte von Linz wird abgerufen (2 Gerichte angelegt).
     * Erwartet: Die Liste "gerichte" hat genau 2 Einträge, in der Reihenfolge
     * Classic Cheeseburger, dann Pommes Frites.
     */
    @Test
    void menuCard_returnsAllDishesOfRestaurant() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte.length()").value(2))
                .andExpect(jsonPath("$.gerichte[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.gerichte[1].name").value("Pommes Frites"));
    }

    /**
     * Was passiert: Speisekarte von Linz wird abgerufen, das erste Gericht wird geprüft.
     * Erwartet: Name "Classic Cheeseburger", Beschreibung "Rindfleisch, Cheddar und Salat",
     * Preis 8.9.
     */
    @Test
    void menuCard_dishHasNameDescriptionAndPrice() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(jsonPath("$.gerichte[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.gerichte[0].description").value("Rindfleisch, Cheddar und Salat"))
                .andExpect(jsonPath("$.gerichte[0].price").value(8.9));
    }

    /**
     * Was passiert: Speisekarte von Graz wird abgerufen (2 Menüs angelegt).
     * Erwartet: Die Liste "menues" hat genau 2 Einträge, in der Reihenfolge
     * Steirer-Menü, dann Kernöl-Menü.
     */
    @Test
    void menuCard_returnsAllMenusOfRestaurant() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menues.length()").value(2))
                .andExpect(jsonPath("$.menues[0].name").value("Steirer-Menü"))
                .andExpect(jsonPath("$.menues[1].name").value("Kernöl-Menü"));
    }

    /**
     * Was passiert: Speisekarte von Linz wird abgerufen, das erste Menü wird geprüft.
     * Erwartet: Name "Burger-Menü", Beschreibung "Cheeseburger, Pommes und Getränk",
     * Preis 11.9.
     */
    @Test
    void menuCard_menuHasNameDescriptionAndPrice() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(jsonPath("$.menues[0].name").value("Burger-Menü"))
                .andExpect(jsonPath("$.menues[0].description").value("Cheeseburger, Pommes und Getränk"))
                .andExpect(jsonPath("$.menues[0].price").value(11.9));
    }

    // ═════════════════════════════ B) Jedes Restaurant hat seine eigene Speisekarte ═════════════════════════════

    /**
     * Was passiert: Speisekarte von Graz wird abgerufen.
     * Erwartet: Name ist Graz, genau 1 Gericht (Kürbiskernöl-Burger) und genau 2 Menüs
     * -> Graz hat andere Daten als Linz (1 Gericht / 2 Menüs statt 2 Gerichte / 1 Menü).
     */
    @Test
    void menuCard_differsPerRestaurant() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value(GRAZ_NAME))
                .andExpect(jsonPath("$.gerichte.length()").value(1))
                .andExpect(jsonPath("$.gerichte[0].name").value("Kürbiskernöl-Burger"))
                .andExpect(jsonPath("$.menues.length()").value(2));
    }

    /**
     * Was passiert: Speisekarte von Graz wird abgerufen.
     * Erwartet: Der "Classic Cheeseburger" und das "Burger-Menü" (beide gehören zu Linz)
     * kommen in der Antwort von Graz NICHT vor.
     */
    @Test
    void menuCard_showsOnlyEntriesOfOwnRestaurant() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Classic Cheeseburger')]").isEmpty())
                .andExpect(jsonPath("$.menues[?(@.name=='Burger-Menü')]").isEmpty());
    }

    // ═════════════════════════════ C) Logik: Rechnen mit Anzahl und Preisen ═════════════════════════════

    /**
     * Rechnung Anzahl (Linz): 2 Gerichte + 1 Menü = 3 Einträge insgesamt.
     * Was passiert: Speisekarte von Linz wird abgerufen, die Einträge beider Listen werden gezählt.
     * Erwartet: gerichte = 2, menues = 1, Summe = 3.
     */
    @Test
    void logic_countOfEntries_linz() throws Exception {
        String json = loadMenuCard(linz.getId());

        int dishes = prices(json, "$.gerichte[*].price").size();
        int menus = prices(json, "$.menues[*].price").size();

        assertThat(dishes).isEqualTo(2);
        assertThat(menus).isEqualTo(1);
        assertThat(dishes + menus).isEqualTo(3);
    }

    /**
     * Rechnung Anzahl (Graz): 1 Gericht + 2 Menüs = 3 Einträge insgesamt.
     * Was passiert: Speisekarte von Graz wird abgerufen, die Einträge beider Listen werden gezählt.
     * Erwartet: gerichte = 1, menues = 2, Summe = 3.
     */
    @Test
    void logic_countOfEntries_graz() throws Exception {
        String json = loadMenuCard(graz.getId());

        int dishes = prices(json, "$.gerichte[*].price").size();
        int menus = prices(json, "$.menues[*].price").size();

        assertThat(dishes).isEqualTo(1);
        assertThat(menus).isEqualTo(2);
        assertThat(dishes + menus).isEqualTo(3);
    }

    /**
     * Rechnung Summe Gerichte (Linz): 8.90 + 3.50 = 12.40.
     * Was passiert: Alle Gerichtspreise von Linz werden aus der Antwort gelesen und addiert.
     * Erwartet: Die Summe ist 12.40.
     */
    @Test
    void logic_sumOfDishPrices_linz() throws Exception {
        String json = loadMenuCard(linz.getId());

        assertThat(sum(prices(json, "$.gerichte[*].price"))).isEqualByComparingTo("12.40");
    }

    /**
     * Rechnung Summe Menüs (Graz): 13.50 + 12.90 = 26.40.
     * Was passiert: Alle Menüpreise von Graz werden aus der Antwort gelesen und addiert.
     * Erwartet: Die Summe ist 26.40.
     */
    @Test
    void logic_sumOfMenuPrices_graz() throws Exception {
        String json = loadMenuCard(graz.getId());

        assertThat(sum(prices(json, "$.menues[*].price"))).isEqualByComparingTo("26.40");
    }

    /**
     * Rechnung Durchschnitt (Linz, Gerichte): (8.90 + 3.50) / 2 = 12.40 / 2 = 6.20.
     * Was passiert: Die Gerichtspreise von Linz werden addiert und durch ihre Anzahl geteilt.
     * Erwartet: Der Durchschnittspreis ist 6.20.
     */
    @Test
    void logic_averageDishPrice_linz() throws Exception {
        String json = loadMenuCard(linz.getId());
        List<BigDecimal> dishPrices = prices(json, "$.gerichte[*].price");

        BigDecimal average = sum(dishPrices).divide(BigDecimal.valueOf(dishPrices.size()));

        assertThat(average).isEqualByComparingTo("6.20");
    }

    /**
     * Rechnung Spanne (Linz, Gerichte): teuerstes 8.90 - günstigstes 3.50 = 5.40.
     * Was passiert: Aus den Gerichtspreisen von Linz werden Maximum und Minimum bestimmt.
     * Erwartet: Maximum = 8.90, Minimum = 3.50, Differenz = 5.40.
     */
    @Test
    void logic_cheapestAndMostExpensiveDish_linz() throws Exception {
        String json = loadMenuCard(linz.getId());
        List<BigDecimal> dishPrices = prices(json, "$.gerichte[*].price");

        BigDecimal max = dishPrices.stream().max(BigDecimal::compareTo).orElseThrow();
        BigDecimal min = dishPrices.stream().min(BigDecimal::compareTo).orElseThrow();

        assertThat(max).isEqualByComparingTo("8.90");
        assertThat(min).isEqualByComparingTo("3.50");
        assertThat(max.subtract(min)).isEqualByComparingTo("5.40");
    }

    /**
     * Rechnung Menü-Ersparnis (Linz): Cheeseburger 8.90 + Pommes 3.50 = 12.40 einzeln.
     * Das Burger-Menü kostet 11.90 -> Ersparnis = 12.40 - 11.90 = 0.50.
     * Was passiert: Der Menüpreis wird mit der Summe der Einzelpreise verglichen.
     * Erwartet: Das Menü ist günstiger als die Einzelpreise, Ersparnis genau 0.50.
     */
    @Test
    void logic_menuIsCheaperThanSingleDishes_linz() throws Exception {
        String json = loadMenuCard(linz.getId());

        BigDecimal singleSum = price(json, "$.gerichte[0].price").add(price(json, "$.gerichte[1].price"));
        BigDecimal menuPrice = price(json, "$.menues[0].price");

        assertThat(menuPrice).isLessThan(singleSum);
        assertThat(singleSum.subtract(menuPrice)).isEqualByComparingTo("0.50");
    }

    /**
     * Rechnung Aufpreis (Graz): Das Kernöl-Menü (12.90) enthält den Burger (9.50) plus Beilage
     * und Getränk -> Aufpreis = 12.90 - 9.50 = 3.40.
     * Was passiert: Der Preis des Kernöl-Menüs wird mit dem Preis des Kürbiskernöl-Burgers verglichen.
     * Erwartet: Das Menü ist teurer als der Burger allein, Differenz genau 3.40.
     */
    @Test
    void logic_menuWithBurgerIsMoreExpensiveThanBurgerAlone_graz() throws Exception {
        String json = loadMenuCard(graz.getId());

        BigDecimal burger = price(json, "$.gerichte[0].price");
        BigDecimal kernoelMenu = price(json, "$.menues[1].price");

        assertThat(kernoelMenu).isGreaterThan(burger);
        assertThat(kernoelMenu.subtract(burger)).isEqualByComparingTo("3.40");
    }

    /**
     * Regel: Ein Preis muss größer als 0 sein und höchstens 2 Nachkommastellen haben (Cent-Genauigkeit).
     * Was passiert: Alle Gerichts- und Menüpreise von Linz und Graz werden geprüft.
     * Erwartet: Jeder Preis > 0 und scale <= 2 (z.B. 8.9 oder 11.90 ok, 8.999 nicht).
     */
    @Test
    void logic_allPricesPositiveAndInCents() throws Exception {
        for (Restaurant restaurant : List.of(linz, graz)) {
            String json = loadMenuCard(restaurant.getId());

            List<BigDecimal> all = prices(json, "$.gerichte[*].price");
            all.addAll(prices(json, "$.menues[*].price"));

            assertThat(all).isNotEmpty();
            for (BigDecimal p : all) {
                assertThat(p).isGreaterThan(BigDecimal.ZERO);
                assertThat(p.stripTrailingZeros().scale()).isLessThanOrEqualTo(2);
            }
        }
    }

    /**
     * Rechnung nach Änderung (Linz): Es wird ein 3. Gericht "Cola" um 5.00 angelegt.
     * Neue Anzahl Gerichte: 2 + 1 = 3. Neue Summe: 12.40 + 5.00 = 17.40.
     * Was passiert: Speisekarte wird NACH dem Anlegen erneut abgerufen.
     * Erwartet: 3 Gerichte, Summe 17.40 -> die Speisekarte ist immer aktuell, nichts ist fest eingebaut.
     */
    @Test
    void logic_newDishChangesCountAndSum_linz() throws Exception {
        saveDish(linz, "Cola", "0,33 Liter", "5.00");

        String json = loadMenuCard(linz.getId());
        List<BigDecimal> dishPrices = prices(json, "$.gerichte[*].price");

        assertThat(dishPrices).hasSize(3);
        assertThat(sum(dishPrices)).isEqualByComparingTo("17.40");
    }

    /**
     * Rechnung Leere Speisekarte (Wien): 0 Gerichte + 0 Menüs = 0 Einträge, Summe = 0.
     * Was passiert: Speisekarte von Wien (ohne Einträge) wird abgerufen und gerechnet.
     * Erwartet: Beide Listen leer, Anzahl 0, Summe 0.00.
     */
    @Test
    void logic_emptyMenuCard_wien_countAndSumAreZero() throws Exception {
        String json = loadMenuCard(wien.getId());

        List<BigDecimal> dishPrices = prices(json, "$.gerichte[*].price");
        List<BigDecimal> menuPrices = prices(json, "$.menues[*].price");

        assertThat(dishPrices.size() + menuPrices.size()).isZero();
        assertThat(sum(dishPrices)).isEqualByComparingTo("0.00");
        assertThat(sum(menuPrices)).isEqualByComparingTo("0.00");
    }

    // ═════════════════════════════ D) Sonderfälle und Fehler ═════════════════════════════

    /**
     * Was passiert: Speisekarte von Wien (Restaurant existiert, hat aber keine Einträge) wird abgerufen.
     * Erwartet: Status 200 (kein Fehler!), Name Wien, beide Listen leer (Länge 0).
     */
    @Test
    void menuCard_restaurantWithoutEntries_emptyListsWith200() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", wien.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value(WIEN_NAME))
                .andExpect(jsonPath("$.gerichte.length()").value(0))
                .andExpect(jsonPath("$.menues.length()").value(0));
    }

    /**
     * Was passiert: Speisekarte für die ID 999999 wird abgerufen (so ein Restaurant gibt es nicht).
     * Erwartet: Status 404 Not Found.
     */
    @Test
    void menuCard_unknownRestaurantId_returns404() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", 999999L))
                .andExpect(status().isNotFound());
    }

    /**
     * Was passiert: Speisekarte für die ID "abc" wird abgerufen (keine Zahl).
     * Erwartet: Status 400 Bad Request.
     */
    @Test
    void menuCard_invalidRestaurantId_returns400() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", "abc"))
                .andExpect(status().isBadRequest());
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    /** Ruft die Speisekarte ab (erwartet 200) und liefert den JSON-Text in UTF-8. */
    private String loadMenuCard(Long restaurantId) throws Exception {
        return mockMvc.perform(get("/restaurant/{id}/speisekarte", restaurantId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    /** Liest alle Zahlen eines JSON-Pfads (z.B. "$.gerichte[*].price") als BigDecimal-Liste. */
    private List<BigDecimal> prices(String json, String path) {
        List<Number> raw = JsonPath.read(json, path);
        return new java.util.ArrayList<>(
                raw.stream().map(n -> BigDecimal.valueOf(n.doubleValue())).toList());
    }

    /** Liest genau eine Zahl eines JSON-Pfads (z.B. "$.menues[0].price") als BigDecimal. */
    private BigDecimal price(String json, String path) {
        Number n = JsonPath.read(json, path);
        return BigDecimal.valueOf(n.doubleValue());
    }

    /** Addiert alle Preise einer Liste (leere Liste ergibt 0). */
    private BigDecimal sum(List<BigDecimal> values) {
        return values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Restaurant saveRestaurant(String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        return restaurantRepo.save(restaurant);
    }

    private void saveDish(Restaurant restaurant, String name, String description, String price) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(new BigDecimal(price));
        dish.setRestaurant(restaurant);
        dishRepo.save(dish);
    }

    private void saveMenu(Restaurant restaurant, String name, String description, String price) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(new BigDecimal(price));
        menu.setRestaurant(restaurant);
        menuRepo.save(menu);
    }
}