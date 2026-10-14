package at.htlkaindorf.backend_redogo.Controller;

import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US1: Als Kunde möchte ich die Speisekarte durchsehen und Gerichte oder Menüs in meinen Warenkorb legen.
 * Black-Box-Tests: geprüft werden nur die HTTP-Aufrufe
 * GET /api/users/{id}/cart und POST /api/users/{id}/cart/items.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private DishRepository dishRepo;

    @Autowired
    private MenuRepository menuRepo;

    private User anna;
    private User max;

    private Dish cheeseburger;   // 8.90
    private Dish pommes;         // 3.50
    private Dish ausverkauft;    // nicht verfügbar
    private Menu burgerMenue;    // 11.90
    private Menu menueAus;       // nicht verfügbar

    @BeforeEach
    void setUpMockData() {
        anna = saveUser("Anna", "anna@muster.at");
        max = saveUser("Max", "max@mustermann.at");

        Restaurant linz = new Restaurant();
        linz.setName("RedoGo Linz Landstraße");
        linz = restaurantRepo.save(linz);

        cheeseburger = saveDish(linz, "Classic Cheeseburger", "8.90", true);
        pommes = saveDish(linz, "Pommes Frites", "3.50", true);
        ausverkauft = saveDish(linz, "Coleslaw", "2.90", false);
        burgerMenue = saveMenu(linz, "Burger-Menü", "11.90", true);
        menueAus = saveMenu(linz, "Crunch-Menü", "11.50", false);
    }

    // ═════════════════════════════ A) Nach dem Hinzufügen ist der Artikel im Warenkorb sichtbar ═════════════════════════════

    // Ein Gericht wird hinzugefügt und in der Antwort geliefert
    @Test
    void addDish_isVisibleInResponse() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(anna.getId()))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].type").value("DISH"))
                .andExpect(jsonPath("$.items[0].itemId").value(cheeseburger.getId()))
                .andExpect(jsonPath("$.items[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.items[0].unitPrice").value(8.9))
                .andExpect(jsonPath("$.items[0].quantity").value(1))
                .andExpect(jsonPath("$.items[0].id").isNotEmpty());
    }

    // Ein Menü wird hinzugefügt und in der Antwort geliefert
    @Test
    void addMenu_isVisibleInResponse() throws Exception {
        addItem(anna, "MENU", burgerMenue.getId(), 1)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].type").value("MENU"))
                .andExpect(jsonPath("$.items[0].itemId").value(burgerMenue.getId()))
                .andExpect(jsonPath("$.items[0].name").value("Burger-Menü"))
                .andExpect(jsonPath("$.items[0].unitPrice").value(11.9));
    }

    // Der Artikel ist auch beim erneuten Abrufen des Warenkorbs noch da
    @Test
    void addedItem_isVisibleWhenFetchingCart() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1);

        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Classic Cheeseburger"));
    }

    // Wird die Menge weggelassen, wird 1 Stück hinzugefügt
    @Test
    void quantityOmitted_defaultsToOne() throws Exception {
        String body = "{\"type\":\"DISH\",\"itemId\":" + cheeseburger.getId() + "}";

        mockMvc.perform(post("/api/users/{id}/cart/items", anna.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items[0].quantity").value(1));
    }

    // ═════════════════════════════ B) Derselbe Artikel zweimal erhöht die Menge ═════════════════════════════

    // Zweimal dasselbe Gericht: ein Eintrag mit Menge 2
    @Test
    void sameDishTwice_increasesQuantityInsteadOfSecondEntry() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1);

        addItem(anna, "DISH", cheeseburger.getId(), 1)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    // Zweimal dasselbe Menü: ein Eintrag mit summierter Menge
    @Test
    void sameMenuWithQuantities_sumsQuantity() throws Exception {
        addItem(anna, "MENU", burgerMenue.getId(), 3);

        addItem(anna, "MENU", burgerMenue.getId(), 2)
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(5));
    }

    // Ein Gericht und ein Menü sind verschiedene Positionen
    @Test
    void dishAndMenuAreSeparateLineItems() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1);

        addItem(anna, "MENU", burgerMenue.getId(), 1)
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    // ═════════════════════════════ C) Mehrere verschiedene Artikel gleichzeitig ═════════════════════════════

    // Mehrere Gerichte und ein Menü liegen gleichzeitig im Warenkorb
    @Test
    void multipleDifferentItems_areInCartSimultaneously() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1);
        addItem(anna, "DISH", pommes.getId(), 1);
        addItem(anna, "MENU", burgerMenue.getId(), 1);

        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.items[1].name").value("Pommes Frites"))
                .andExpect(jsonPath("$.items[2].name").value("Burger-Menü"));
    }

    // Jeder Benutzer hat seinen eigenen Warenkorb
    @Test
    void cartsOfDifferentUsersAreSeparate() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 1);

        mockMvc.perform(get("/api/users/{id}/cart", max.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(0));
    }

    // ═════════════════════════════ D) Ungültige Menge wird mit Fehlermeldung abgelehnt ═════════════════════════════

    // Negative Menge: 400 mit Fehlermeldung
    @Test
    void negativeQuantity_isRejectedWithErrorMessage() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), -1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Die Menge muss mindestens 1 sein"));
    }

    // Menge 0 ergibt keinen Sinn und wird ebenfalls abgelehnt
    @Test
    void quantityZero_isRejected() throws Exception {
        addItem(anna, "MENU", burgerMenue.getId(), 0)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Die Menge muss mindestens 1 sein"));
    }

    // Eine abgelehnte Anfrage verändert den Warenkorb nicht
    @Test
    void rejectedQuantity_doesNotChangeCart() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 2);
        addItem(anna, "DISH", cheeseburger.getId(), -5).andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    // Eine unrealistisch hohe Menge wird abgelehnt (Schutz vor Überlauf)
    @Test
    void tooHighQuantity_isRejected() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 100)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());

        addItem(anna, "DISH", cheeseburger.getId(), 60);
        addItem(anna, "DISH", cheeseburger.getId(), 60)
                .andExpect(status().isBadRequest());
    }

    // ═════════════════════════════ E) Gesamtpreis wird korrekt berechnet ═════════════════════════════

    // Gesamtpreis = Summe aus Einzelpreis x Menge über alle Artikel
    @Test
    void totalPrice_isCalculatedFromAllItemsAndQuantities() throws Exception {
        addItem(anna, "DISH", cheeseburger.getId(), 2);   // 2 x 8.90  = 17.80
        addItem(anna, "DISH", pommes.getId(), 1);         // 1 x 3.50  =  3.50
        addItem(anna, "MENU", burgerMenue.getId(), 1);    // 1 x 11.90 = 11.90

        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(jsonPath("$.items[0].lineTotal").value(17.8))
                .andExpect(jsonPath("$.items[1].lineTotal").value(3.5))
                .andExpect(jsonPath("$.items[2].lineTotal").value(11.9))
                .andExpect(jsonPath("$.totalPrice").value(33.2));
    }

    // Der Gesamtpreis wächst, wenn die Menge eines Artikels erhöht wird
    @Test
    void totalPrice_updatesOnQuantityChange() throws Exception {
        addItem(anna, "DISH", pommes.getId(), 1)
                .andExpect(jsonPath("$.totalPrice").value(3.5));

        addItem(anna, "DISH", pommes.getId(), 2)
                .andExpect(jsonPath("$.totalPrice").value(10.5));
    }

    // Ein leerer Warenkorb kostet 0
    @Test
    void emptyCart_hasTotalPriceZero() throws Exception {
        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(0))
                .andExpect(jsonPath("$.totalPrice").value(0.0));
    }

    // ═════════════════════════════ F) Nicht verfügbare Artikel können nicht hinzugefügt werden ═════════════════════════════

    // Ein nicht verfügbares Gericht wird abgelehnt
    @Test
    void unavailableDish_isRejected() throws Exception {
        addItem(anna, "DISH", ausverkauft.getId(), 1)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("'Coleslaw' ist derzeit nicht verfügbar"));
    }

    // Ein nicht verfügbares Menü wird abgelehnt
    @Test
    void unavailableMenu_isRejected() throws Exception {
        addItem(anna, "MENU", menueAus.getId(), 1)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("'Crunch-Menü' ist derzeit nicht verfügbar"));
    }

    // Nach der Ablehnung ist der Warenkorb unverändert leer
    @Test
    void unavailableItem_doesNotEndUpInCart() throws Exception {
        addItem(anna, "DISH", ausverkauft.getId(), 1).andExpect(status().isConflict());

        mockMvc.perform(get("/api/users/{id}/cart", anna.getId()))
                .andExpect(jsonPath("$.items.length()").value(0));
    }

    // Die Speisekarte kennzeichnet nicht verfügbare Einträge, damit sie angezeigt, aber nicht bestellt werden
    @Test
    void menuCard_marksAvailability() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", cheeseburger.getRestaurant().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte[?(@.name=='Classic Cheeseburger')].available").value(true))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Coleslaw')].available").value(false))
                .andExpect(jsonPath("$.menues[?(@.name=='Crunch-Menü')].available").value(false));
    }

    // ═════════════════════════════ G) Sonderfälle und Fehler ═════════════════════════════

    // Unbekanntes Gericht: 404
    @Test
    void unknownDish_returns404() throws Exception {
        addItem(anna, "DISH", 999999L, 1)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    // Unbekanntes Menü: 404
    @Test
    void unknownMenu_returns404() throws Exception {
        addItem(anna, "MENU", 999999L, 1)
                .andExpect(status().isNotFound());
    }

    // Unbekannter Benutzer: 404 beim Hinzufügen und beim Abrufen
    @Test
    void unknownUser_returns404() throws Exception {
        addItem(999999L, "DISH", cheeseburger.getId(), 1)
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/users/{id}/cart", 999999L))
                .andExpect(status().isNotFound());
    }

    // Fehlende Artikelart bzw. -ID: 400 mit Fehlermeldung
    @Test
    void missingFields_return400() throws Exception {
        mockMvc.perform(post("/api/users/{id}/cart/items", anna.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemId\":" + cheeseburger.getId() + ",\"quantity\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Die Art des Artikels (DISH oder MENU) fehlt"));

        mockMvc.perform(post("/api/users/{id}/cart/items", anna.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"DISH\",\"quantity\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Die ID des Artikels fehlt"));
    }

    // Eine unbekannte Artikelart wird mit 400 abgelehnt
    @Test
    void unknownItemType_returns400() throws Exception {
        addItem(anna, "GETRAENK", cheeseburger.getId(), 1)
                .andExpect(status().isBadRequest());
    }

    // Ein Benutzer ohne Warenkorb bekommt einen leeren Warenkorb (kein Fehler)
    @Test
    void userWithoutCart_getsEmptyCart() throws Exception {
        mockMvc.perform(get("/api/users/{id}/cart", max.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(max.getId()))
                .andExpect(jsonPath("$.items").isArray());
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    private ResultActions addItem(User user, String type, Long itemId, int quantity) throws Exception {
        return addItem(user.getId(), type, itemId, quantity);
    }

    private ResultActions addItem(Long userId, String type, Long itemId, int quantity) throws Exception {
        String body = String.format("{\"type\":\"%s\",\"itemId\":%d,\"quantity\":%d}", type, itemId, quantity);
        return mockMvc.perform(post("/api/users/{id}/cart/items", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }

    private User saveUser(String vorname, String email) {
        return userRepo.save(User.builder()
                .vorname(vorname).nachname("Test").email(email)
                .strasse("Hauptstraße").hausnummer("1").plz("4020").ort("Linz")
                .passwort("Passwort123").build());
    }

    private Dish saveDish(Restaurant restaurant, String name, String price, boolean available) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(name + " (Test)");
        dish.setPrice(new BigDecimal(price));
        dish.setAvailable(available);
        dish.setRestaurant(restaurant);
        return dishRepo.save(dish);
    }

    private Menu saveMenu(Restaurant restaurant, String name, String price, boolean available) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(name + " (Test)");
        menu.setPrice(new BigDecimal(price));
        menu.setAvailable(available);
        menu.setRestaurant(restaurant);
        return menuRepo.save(menu);
    }
}