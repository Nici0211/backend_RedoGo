package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US11: Als Restaurantinhaber möchte ich Gerichte von der Speisekarte meines Restaurants löschen können.
 * Black-Box-Tests für das Zusammenspiel mit dem Warenkorb (US1/US7): Liegt ein gelöschtes Gericht
 * in Warenkörben von Kunden, muss das Löschen trotzdem funktionieren (204 statt 500), die Position muss aus
 * allen betroffenen Warenkörben verschwinden und alles andere muss unverändert bleiben.
 *
 * <p>Alles wird nur über HTTP-Aufrufe angelegt und geprüft:
 * POST /api/admin/{adminId}/restaurants/{restaurantId}/dishes,
 * POST/GET/PUT/DELETE /api/users/{userId}/cart/...,
 * DELETE /api/admin/{adminId}/restaurants/{restaurantId}/dishes/{dishId}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminDishDeleteCartControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepo;
    @Autowired private RestaurantRepository restaurantRepo;

    private User admin;
    private User otherAdmin;
    private User customer;      // Anna
    private User customer2;     // Max
    private User bystander;     // Kunde, der mit den gelöschten Artikeln nichts zu tun hat
    private Restaurant ownRestaurant;
    private Restaurant foreignRestaurant;

    @BeforeEach
    void setUp() {
        admin = saveUser("admin@test.at", Role.ADMIN);
        otherAdmin = saveUser("other@test.at", Role.ADMIN);
        customer = saveUser("anna@test.at", Role.USER);
        customer2 = saveUser("max@test.at", Role.USER);
        bystander = saveUser("lisa@test.at", Role.USER);

        ownRestaurant = saveRestaurant("Eigenes Restaurant", admin);
        foreignRestaurant = saveRestaurant("Fremdes Restaurant", otherAdmin);
    }

    // ---------- Helper ----------

    private String adminBase(User user, Restaurant restaurant) {
        return "/api/admin/" + user.getId() + "/restaurants/" + restaurant.getId();
    }

    private long createEntry(String type, User user, Restaurant restaurant, String name, String price) throws Exception {
        String response = mockMvc.perform(post(adminBase(user, restaurant) + "/" + type)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\",\"price\":" + price + "}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(response, "$.id")).longValue();
    }

    private long createDish(User user, Restaurant restaurant, String name, String price) throws Exception {
        return createEntry("dishes", user, restaurant, name, price);
    }

    private ResultActions deleteDish(User user, Restaurant restaurant, Object dishId) throws Exception {
        return mockMvc.perform(delete(adminBase(user, restaurant) + "/dishes/" + dishId));
    }

    private ResultActions addToCart(User user, String type, long itemId, int quantity) throws Exception {
        return mockMvc.perform(post("/api/users/" + user.getId() + "/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"" + type + "\",\"itemId\":" + itemId + ",\"quantity\":" + quantity + "}"));
    }

    /** Legt den Artikel in den Warenkorb und liefert die ID der Warenkorb-Position (nicht die des Gerichts). */
    private long addToCartAndGetPositionId(User user, String type, long itemId, int quantity) throws Exception {
        String response = addToCart(user, type, itemId, quantity)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        List<Number> ids = JsonPath.read(response,
                "$.items[?(@.type=='" + type + "' && @.itemId==" + itemId + ")].id");
        return ids.get(0).longValue();
    }

    private ResultActions getCart(User user) throws Exception {
        return mockMvc.perform(get("/api/users/" + user.getId() + "/cart"));
    }

    private void assertCartEmpty(User user) throws Exception {
        getCart(user)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(0)))
                .andExpect(jsonPath("$.totalPrice").value(0.0));
    }

    // =====================================================================
    // Gericht im Warenkorb löschen
    // =====================================================================

    /**
     * Kernfall von US11: Ein Gericht liegt im Warenkorb eines Kunden und wird vom Inhaber gelöscht
     * (parametrisiert mit verschiedenen Mengen, auch dem Maximum 99).
     *
     * <p>Erwartet: 204 No Content (kein 500 wegen Fremdschlüssel); der Warenkorb des Kunden ist danach leer
     * (0 Positionen, Gesamtpreis 0).
     */
    @ParameterizedTest(name = "Gericht mit Menge {0} im Warenkorb kann gelöscht werden")
    @ValueSource(ints = {1, 2, 99})
    void gericht_im_warenkorb_kann_geloescht_werden(int quantity) throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        addToCart(customer, "DISH", dishId, quantity).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, dishId)
                .andExpect(status().isNoContent())
                .andExpect(content().string(emptyString()));

        assertCartEmpty(customer);
    }

    /**
     * Der Warenkorb selbst bleibt bestehen, es verschwindet nur die Position.
     *
     * <p>Erwartet: Die Warenkorb-ID vor und nach dem Löschen ist gleich; der Kunde bleibt dem Warenkorb zugeordnet.
     */
    @Test
    void warenkorb_bleibt_nach_dem_loeschen_des_gerichts_bestehen() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        String before = addToCart(customer, "DISH", dishId, 1)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long cartIdBefore = ((Number) JsonPath.read(before, "$.id")).longValue();

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        getCart(customer)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartIdBefore))
                .andExpect(jsonPath("$.userId").value(customer.getId()));
    }

    /**
     * Gezieltes Entfernen: Liegen zwei Gerichte im Warenkorb, verschwindet nur das gelöschte.
     * Menge und Preis der übrigen Position bleiben erhalten, der Gesamtpreis wird neu berechnet.
     *
     * <p>Erwartet: 204; im Warenkorb bleibt nur "Pommes" mit Menge 2; Gesamtpreis 7.00 (2 x 3.50).
     */
    @Test
    void andere_positionen_bleiben_erhalten_und_gesamtpreis_stimmt() throws Exception {
        long burgerId = createDish(admin, ownRestaurant, "Burger", "8.90");
        long pommesId = createDish(admin, ownRestaurant, "Pommes", "3.50");
        addToCart(customer, "DISH", burgerId, 1).andExpect(status().isCreated());
        addToCart(customer, "DISH", pommesId, 2).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, burgerId).andExpect(status().isNoContent());

        getCart(customer)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].type").value("DISH"))
                .andExpect(jsonPath("$.items[0].itemId").value(pommesId))
                .andExpect(jsonPath("$.items[0].name").value("Pommes"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].lineTotal").value(7.0))
                .andExpect(jsonPath("$.totalPrice").value(7.0));
    }

    /**
     * Mehrere Kunden: Das Gericht liegt in den Warenkörben von zwei Kunden (mit unterschiedlicher Menge).
     *
     * <p>Erwartet: 204; das Gericht ist bei beiden Kunden weg; die andere Position von Max bleibt erhalten.
     */
    @Test
    void gericht_wird_aus_den_warenkoerben_aller_kunden_entfernt() throws Exception {
        long burgerId = createDish(admin, ownRestaurant, "Burger", "8.90");
        long pommesId = createDish(admin, ownRestaurant, "Pommes", "3.50");
        addToCart(customer, "DISH", burgerId, 1).andExpect(status().isCreated());
        addToCart(customer2, "DISH", burgerId, 3).andExpect(status().isCreated());
        addToCart(customer2, "DISH", pommesId, 1).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, burgerId).andExpect(status().isNoContent());

        assertCartEmpty(customer);
        getCart(customer2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(pommesId))
                .andExpect(jsonPath("$.totalPrice").value(3.5));
    }

    /**
     * Unbeteiligte Kunden: Der Warenkorb eines Kunden, der das gelöschte Gericht gar nicht hat, bleibt unverändert.
     *
     * <p>Erwartet: 204; der Warenkorb des Unbeteiligten enthält weiterhin seine Position mit unveränderter Menge.
     */
    @Test
    void warenkorb_eines_unbeteiligten_kunden_bleibt_unveraendert() throws Exception {
        long burgerId = createDish(admin, ownRestaurant, "Burger", "8.90");
        long pizzaId = createDish(admin, ownRestaurant, "Pizza", "9.90");
        addToCart(customer, "DISH", burgerId, 1).andExpect(status().isCreated());
        addToCart(bystander, "DISH", pizzaId, 4).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, burgerId).andExpect(status().isNoContent());

        getCart(bystander)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(pizzaId))
                .andExpect(jsonPath("$.items[0].quantity").value(4))
                .andExpect(jsonPath("$.totalPrice").value(39.6));
    }

    /**
     * Datentrennung zwischen Restaurants: Ein Kunde hat "Burger" aus dem eigenen und "Burger" aus dem fremden
     * Restaurant im Warenkorb. Gelöscht wird nur das Gericht im eigenen Restaurant.
     *
     * <p>Erwartet: 204; im Warenkorb bleibt genau das Gericht des fremden Restaurants (richtige Gericht-ID).
     */
    @Test
    void gleichnamiges_gericht_aus_fremdem_restaurant_bleibt_im_warenkorb() throws Exception {
        long ownBurger = createDish(admin, ownRestaurant, "Burger", "8.90");
        long foreignBurger = createDish(otherAdmin, foreignRestaurant, "Burger", "9.50");
        addToCart(customer, "DISH", ownBurger, 1).andExpect(status().isCreated());
        addToCart(customer, "DISH", foreignBurger, 1).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, ownBurger).andExpect(status().isNoContent());

        getCart(customer)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(foreignBurger))
                .andExpect(jsonPath("$.totalPrice").value(9.5));
    }

    // =====================================================================
    // Verhalten nach dem Löschen
    // =====================================================================

    /**
     * Ein gelöschtes Gericht kann nicht mehr in einen Warenkorb gelegt werden.
     *
     * <p>Erwartet: 404 Not Found mit Meldung "Gericht mit der ID {id} wurde nicht gefunden"; der Warenkorb bleibt leer.
     */
    @Test
    void geloeschtes_gericht_kann_nicht_mehr_in_den_warenkorb_gelegt_werden() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        addToCart(customer, "DISH", dishId, 1)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Gericht mit der ID " + dishId + " wurde nicht gefunden"));

        assertCartEmpty(customer);
    }

    /**
     * Die entfernte Warenkorb-Position kann der Kunde nicht mehr ändern oder entfernen (keine "Geisterposition").
     *
     * <p>Erwartet: PUT und DELETE auf die alte Position-ID liefern 404 mit Meldung
     * "Die Position mit der ID {id} ist nicht im Warenkorb".
     */
    @Test
    void entfernte_position_kann_nicht_mehr_geaendert_oder_entfernt_werden() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        long positionId = addToCartAndGetPositionId(customer, "DISH", dishId, 1);

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        mockMvc.perform(put("/api/users/" + customer.getId() + "/cart/items/" + positionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":5}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Die Position mit der ID " + positionId + " ist nicht im Warenkorb"));

        mockMvc.perform(delete("/api/users/" + customer.getId() + "/cart/items/" + positionId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Die Position mit der ID " + positionId + " ist nicht im Warenkorb"));
    }

    /**
     * Neues Gericht mit altem Namen: Wird "Burger" nach dem Löschen neu angelegt, ist es ein neuer Artikel
     * und taucht nicht automatisch im alten Warenkorb auf; beim Hinzufügen beginnt die Menge bei 1
     * (kein Aufsummieren mit der alten Position).
     *
     * <p>Erwartet: Warenkorb nach dem Löschen leer; nach dem Hinzufügen des neuen "Burger" genau 1 Position
     * mit der neuen Gericht-ID und Menge 1.
     */
    @Test
    void neu_angelegtes_gericht_gleichen_namens_ist_ein_neuer_artikel() throws Exception {
        long oldId = createDish(admin, ownRestaurant, "Burger", "8.90");
        addToCart(customer, "DISH", oldId, 5).andExpect(status().isCreated());
        deleteDish(admin, ownRestaurant, oldId).andExpect(status().isNoContent());

        long newId = createDish(admin, ownRestaurant, "Burger", "9.90");
        assertCartEmpty(customer);

        addToCart(customer, "DISH", newId, 1).andExpect(status().isCreated());

        getCart(customer)
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(newId))
                .andExpect(jsonPath("$.items[0].quantity").value(1))
                .andExpect(jsonPath("$.totalPrice").value(9.9));
    }

    /**
     * Der Warenkorb ist nach dem Löschen weiter benutzbar: Ein anderes Gericht kann hinzugefügt werden.
     *
     * <p>Erwartet: 201 Created; der Warenkorb enthält genau das neu hinzugefügte Gericht.
     */
    @Test
    void warenkorb_ist_nach_dem_loeschen_weiter_benutzbar() throws Exception {
        long burgerId = createDish(admin, ownRestaurant, "Burger", "8.90");
        long pizzaId = createDish(admin, ownRestaurant, "Pizza", "9.90");
        addToCart(customer, "DISH", burgerId, 1).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, burgerId).andExpect(status().isNoContent());

        addToCart(customer, "DISH", pizzaId, 2)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(pizzaId))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.totalPrice").value(19.8));
    }

    /**
     * Zusammenspiel mit der Speisekarte: Ein Gericht, das in einem Warenkorb lag, verschwindet auch von der
     * Speisekarte.
     *
     * <p>Erwartet: Speisekarte enthält nach dem Löschen 0 Gerichte (vorher 1).
     */
    @Test
    void gericht_aus_warenkorb_verschwindet_auch_von_der_speisekarte() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        addToCart(customer, "DISH", dishId, 1).andExpect(status().isCreated());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(jsonPath("$.gerichte", hasSize(1)));

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(0)));
    }

    // =====================================================================
    // Abgelehnte Löschungen dürfen Warenkörbe nicht verändern
    // =====================================================================

    /**
     * Keine Seiteneffekte bei abgelehnten Anfragen: Rolle falsch (403), fremder Admin (403), Gericht gehört zu
     * einem anderen Restaurant (404) und unbekanntes Gericht (404) dürfen keine Warenkorb-Positionen entfernen.
     *
     * <p>Erwartet: Alle Löschanfragen scheitern; die Warenkörbe von Anna (eigenes Gericht) und Max
     * (Gericht des fremden Restaurants) enthalten weiterhin je ihre Position.
     */
    @Test
    void abgelehnte_loeschungen_veraendern_keine_warenkoerbe() throws Exception {
        long ownDish = createDish(admin, ownRestaurant, "Burger", "8.90");
        long foreignDish = createDish(otherAdmin, foreignRestaurant, "Burger", "9.50");
        addToCart(customer, "DISH", ownDish, 1).andExpect(status().isCreated());
        addToCart(customer2, "DISH", foreignDish, 1).andExpect(status().isCreated());

        deleteDish(customer, ownRestaurant, ownDish).andExpect(status().isForbidden());
        deleteDish(otherAdmin, ownRestaurant, ownDish).andExpect(status().isForbidden());
        deleteDish(admin, ownRestaurant, foreignDish).andExpect(status().isNotFound());
        deleteDish(admin, ownRestaurant, 999999).andExpect(status().isNotFound());

        getCart(customer)
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(ownDish))
                .andExpect(jsonPath("$.items[0].quantity").value(1));
        getCart(customer2)
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].itemId").value(foreignDish))
                .andExpect(jsonPath("$.items[0].quantity").value(1));
    }

    /**
     * Zweimal löschen: Das zweite Löschen eines Gerichts, das im Warenkorb lag, liefert 404
     * und verändert den (inzwischen leeren) Warenkorb nicht.
     *
     * <p>Erwartet: Erstes Löschen 204, zweites Löschen 404; der Warenkorb ist weiterhin leer.
     */
    @Test
    void zweites_loeschen_eines_gerichts_aus_dem_warenkorb_liefert_404() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger", "8.90");
        addToCart(customer, "DISH", dishId, 2).andExpect(status().isCreated());

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());
        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNotFound());

        assertCartEmpty(customer);
    }

    // ---------- Helper (wie in AdminDishControllerTest) ----------

    private User saveUser(String email, Role role) {
        return userRepo.save(User.builder()
                .vorname("Test").nachname("User").email(email)
                .strasse("Teststraße").hausnummer("1").plz("8230").ort("Hartberg")
                .passwort("Passwort123").role(role).build());
    }

    private Restaurant saveRestaurant(String name, User owner) {
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setOwner(owner);
        return restaurantRepo.save(r);
    }
}
