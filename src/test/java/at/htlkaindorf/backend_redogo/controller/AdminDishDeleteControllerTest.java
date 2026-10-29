package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
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

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US11: Als Restaurantinhaber möchte ich Gerichte von der Speisekarte meines Restaurants löschen können.
 * Black-Box-Tests über DELETE /api/admin/{adminId}/restaurants/{restaurantId}/dishes/{dishId}.
 * Gerichte werden ausschließlich über den öffentlichen POST-Endpunkt angelegt.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminDishDeleteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepo;
    @Autowired private RestaurantRepository restaurantRepo;
    @Autowired private DishRepository dishRepo;

    private User admin;
    private User otherAdmin;
    private User customer;
    private Restaurant ownRestaurant;
    private Restaurant foreignRestaurant;

    @BeforeEach
    void setUp() {
        admin = saveUser("admin@test.at", Role.ADMIN);
        otherAdmin = saveUser("other@test.at", Role.ADMIN);
        customer = saveUser("kunde@test.at", Role.USER);

        ownRestaurant = saveRestaurant("Eigenes Restaurant", admin);
        foreignRestaurant = saveRestaurant("Fremdes Restaurant", otherAdmin);
    }

    // ---------- Helper ----------

    private String base(User user, Restaurant restaurant) {
        return "/api/admin/" + user.getId() + "/restaurants/" + restaurant.getId();
    }

    private ResultActions deleteDish(User user, Restaurant restaurant, Object dishId) throws Exception {
        return mockMvc.perform(delete(base(user, restaurant) + "/dishes/" + dishId));
    }


    private long createEntry(String type, User user, Restaurant restaurant, String name) throws Exception {
        String json = "{\"name\":\"" + name + "\",\"price\":9.90}";
        String response = mockMvc.perform(post(base(user, restaurant) + "/" + type)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(response, "$.id")).longValue();
    }

    private long createDish(User user, Restaurant restaurant, String name) throws Exception {
        return createEntry("dishes", user, restaurant, name);
    }


    private ResultActions createDishRaw(User user, Restaurant restaurant, String name) throws Exception {
        return mockMvc.perform(post(base(user, restaurant) + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\",\"price\":9.90}"));
    }

    // =====================================================================
    // Gericht löschen: Happy Path
    // =====================================================================

    /**
     * Happy Path: Ein Admin löscht ein Gericht aus seinem eigenen Restaurant.
     *
     * <p>Erwartet: 204 No Content mit leerem Body; das Gericht ist nicht mehr in der Datenbank (0 Gerichte).
     */
    @Test
    void admin_kann_gericht_aus_eigenem_restaurant_loeschen() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");
        assertEquals(1, dishRepo.count());

        deleteDish(admin, ownRestaurant, dishId)
                .andExpect(status().isNoContent())
                .andExpect(content().string(emptyString()));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Zusammenspiel mit der Speisekarte (US28): Ein gelöschtes Gericht darf für Kunden nicht mehr sichtbar sein.
     *
     * <p>Erwartet: Vor dem Löschen enthält die Speisekarte 1 Gericht, danach 0.
     */
    @Test
    void geloeschtes_gericht_verschwindet_von_der_speisekarte() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Gulasch");

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(1)));

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(0)));
    }

    // =====================================================================
    // Gericht löschen: Logik
    // =====================================================================

    /**
     * Gezieltes Löschen: Es wird nur das angegebene Gericht entfernt, alle anderen im Restaurant bleiben erhalten.
     *
     * <p>Erwartet: 204; von 3 Gerichten bleiben 2 übrig, und zwar Burger und Pizza (nicht Gulasch).
     */
    @Test
    void nur_das_angegebene_gericht_wird_geloescht() throws Exception {
        createDish(admin, ownRestaurant, "Burger");
        long gulaschId = createDish(admin, ownRestaurant, "Gulasch");
        createDish(admin, ownRestaurant, "Pizza");

        deleteDish(admin, ownRestaurant, gulaschId).andExpect(status().isNoContent());

        assertEquals(2, dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).size());
        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(2)))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Gulasch')]", hasSize(0)))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Burger')]", hasSize(1)))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Pizza')]", hasSize(1)));
    }

    /**
     * Datentrennung: Das Löschen in einem Restaurant verändert die Speisekarte eines anderen Restaurants nicht,
     * auch wenn dort ein Gericht mit demselben Namen existiert.
     *
     * <p>Erwartet: 204; das "Burger" im fremden Restaurant bleibt bestehen (1 Gericht in der Datenbank).
     */
    @Test
    void loeschen_veraendert_gericht_gleichen_namens_in_anderem_restaurant_nicht() throws Exception {
        long ownId = createDish(admin, ownRestaurant, "Burger");
        createDish(otherAdmin, foreignRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, ownId).andExpect(status().isNoContent());

        assertEquals(0, dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).size());
        assertEquals(1, dishRepo.findByRestaurantIdOrderByIdAsc(foreignRestaurant.getId()).size());
    }

    /**
     * Wiederverwendbarkeit des Namens: Nach dem Löschen darf ein Gericht mit demselben Namen wieder angelegt werden
     * (die Eindeutigkeitsprüfung darf keine "Leichen" berücksichtigen).
     *
     * <p>Erwartet: Löschen 204; erneutes Anlegen von "Burger" 201 (kein 409); das neue Gericht hat eine andere ID.
     */
    @Test
    void name_kann_nach_dem_loeschen_erneut_verwendet_werden() throws Exception {
        long firstId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, firstId).andExpect(status().isNoContent());

        String response = createDishRaw(admin, ownRestaurant, "Burger")
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long secondId = ((Number) JsonPath.read(response, "$.id")).longValue();

        assertTrue(secondId != firstId, "Neues Gericht muss eine neue ID bekommen");
        assertEquals(1, dishRepo.count());
    }

    /**
     * Doppeltes Löschen: Dasselbe Gericht kann nicht zweimal gelöscht werden.
     *
     * <p>Erwartet: Erstes Löschen 204, zweites Löschen 404 Not Found (status=404).
     */
    @Test
    void gericht_zweimal_loeschen_liefert_beim_zweiten_mal_404() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());
        deleteDish(admin, ownRestaurant, dishId)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    /**
     * Logische Zuordnung: Ein Gericht, das zu einem anderen Restaurant gehört, darf nicht über den Pfad des eigenen
     * Restaurants gelöscht werden (Pfad-Manipulation: eigene Restaurant-ID, fremde Gericht-ID).
     *
     * <p>Erwartet: 404 Not Found mit Meldung "Gericht mit der ID {id} wurde in diesem Restaurant nicht gefunden";
     * das fremde Gericht bleibt bestehen.
     */
    @Test
    void gericht_eines_anderen_restaurants_kann_nicht_ueber_eigenes_restaurant_geloescht_werden() throws Exception {
        long foreignDishId = createDish(otherAdmin, foreignRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, foreignDishId)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Gericht mit der ID " + foreignDishId + " wurde in diesem Restaurant nicht gefunden"));

        assertEquals(1, dishRepo.findByRestaurantIdOrderByIdAsc(foreignRestaurant.getId()).size());
    }

    /**
     * Das Löschen eines Gerichts verändert die Restaurantliste des Admins nicht.
     *
     * <p>Erwartet: 200 OK; die Liste enthält weiterhin genau das eigene Restaurant.
     */
    @Test
    void restaurant_bleibt_nach_loeschen_des_gerichts_erhalten() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, dishId).andExpect(status().isNoContent());

        mockMvc.perform(get("/api/admin/" + admin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Eigenes Restaurant"));
    }

    // =====================================================================
    // Gericht löschen: Berechtigungen (403)
    // =====================================================================

    /**
     * Rollenprüfung: Ein Kunde (Rolle USER) darf keine Gerichte löschen.
     *
     * <p>Erwartet: 403 Forbidden, status=403 und Meldung "Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten";
     * das Gericht bleibt bestehen.
     */
    @Test
    void normaler_user_darf_gericht_nicht_loeschen() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(customer, ownRestaurant, dishId)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Besitzprüfung: Ein Admin darf keine Gerichte in Restaurants löschen, deren Inhaber er nicht ist.
     *
     * <p>Erwartet: 403 Forbidden mit Meldung "Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten";
     * das Gericht bleibt bestehen.
     */
    @Test
    void admin_darf_gericht_in_fremdem_restaurant_nicht_loeschen() throws Exception {
        long foreignDishId = createDish(otherAdmin, foreignRestaurant, "Burger");

        deleteDish(admin, foreignRestaurant, foreignDishId)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Randfall: Das Restaurant hat keinen Inhaber (owner = null); niemand darf dort löschen.
     *
     * <p>Erwartet: 403 Forbidden; das Gericht bleibt bestehen.
     */
    @Test
    void restaurant_ohne_inhaber_liefert_beim_loeschen_403() throws Exception {
        Restaurant ownerless = saveRestaurant("Herrenloses Restaurant", null);
        long dishId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownerless, dishId).andExpect(status().isForbidden());

        assertEquals(1, dishRepo.count());
    }

    // =====================================================================
    // Gericht löschen: 404 und Reihenfolge der Prüfungen
    // =====================================================================

    /**
     * Gericht nicht gefunden: Benutzer und Restaurant existieren, die Gericht-ID nicht.
     *
     * <p>Erwartet: 404 Not Found mit status=404 und Meldung "Gericht mit der ID 999999 wurde in diesem Restaurant nicht gefunden";
     * vorhandene Gerichte bleiben unverändert.
     */
    @Test
    void unbekanntes_gericht_liefert_404() throws Exception {
        createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, 999999)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Gericht mit der ID 999999 wurde in diesem Restaurant nicht gefunden"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Benutzer nicht gefunden: Die Admin-ID im Pfad gehört zu keinem Benutzer.
     *
     * <p>Erwartet: 404 Not Found mit Meldung "Benutzer mit der ID 999999 wurde nicht gefunden"; das Gericht bleibt bestehen.
     */
    @Test
    void unbekannter_benutzer_liefert_beim_loeschen_404() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        mockMvc.perform(delete("/api/admin/999999/restaurants/" + ownRestaurant.getId() + "/dishes/" + dishId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Benutzer mit der ID 999999 wurde nicht gefunden"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Restaurant nicht gefunden: Der Admin existiert, die Restaurant-ID nicht.
     *
     * <p>Erwartet: 404 Not Found mit Meldung "Restaurant mit der ID 999999 wurde nicht gefunden"; das Gericht bleibt bestehen.
     */
    @Test
    void unbekanntes_restaurant_liefert_beim_loeschen_404() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        mockMvc.perform(delete("/api/admin/" + admin.getId() + "/restaurants/999999/dishes/" + dishId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Restaurant mit der ID 999999 wurde nicht gefunden"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Reihenfolge der Prüfungen: Fehlen Benutzer, Restaurant und Gericht, wird zuerst der Benutzer geprüft.
     *
     * <p>Erwartet: 404 Not Found mit Benutzer-Meldung.
     */
    @Test
    void alles_unbekannt_liefert_404_fuer_den_benutzer() throws Exception {
        mockMvc.perform(delete("/api/admin/999997/restaurants/999998/dishes/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Benutzer mit der ID 999997 wurde nicht gefunden"));
    }

    /**
     * Reihenfolge der Prüfungen: Die Rollenprüfung kommt vor der Restaurant-Suche,
     * damit Nicht-Admins nicht erfahren, ob ein Restaurant existiert.
     *
     * <p>Erwartet: 403 Forbidden (nicht 404) mit Meldung "Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten".
     */
    @Test
    void normaler_user_bekommt_beim_loeschen_in_unbekanntem_restaurant_403_statt_404() throws Exception {
        mockMvc.perform(delete("/api/admin/" + customer.getId() + "/restaurants/999999/dishes/999999"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten"));
    }

    /**
     * Reihenfolge der Prüfungen: Die Besitzprüfung kommt vor der Gericht-Suche,
     * damit ein fremder Admin nicht erfährt, ob ein Gericht in einem fremden Restaurant existiert.
     *
     * <p>Erwartet: 403 Forbidden (nicht 404), auch wenn die Gericht-ID nicht existiert.
     */
    @Test
    void admin_bekommt_bei_fremdem_restaurant_und_unbekanntem_gericht_403_statt_404() throws Exception {
        deleteDish(admin, foreignRestaurant, 999999).andExpect(status().isForbidden());
    }

    // =====================================================================
    // Gericht löschen: fehlerhafte Anfragen
    // =====================================================================

    /**
     * Fehlerhafte Anfrage: adminId, restaurantId bzw. dishId im Pfad sind keine Zahlen ("abc").
     *
     * <p>Erwartet: Jeweils 400 Bad Request.
     */
    @Test
    void nicht_numerische_ids_im_pfad_liefern_beim_loeschen_400() throws Exception {
        mockMvc.perform(delete("/api/admin/abc/restaurants/" + ownRestaurant.getId() + "/dishes/1"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/api/admin/" + admin.getId() + "/restaurants/abc/dishes/1"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/api/admin/" + admin.getId() + "/restaurants/" + ownRestaurant.getId() + "/dishes/abc"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Grenzwerte der ID: Negative ID, 0 und eine ID außerhalb des Long-Bereichs.
     *
     * <p>Erwartet: Negative ID und 0 liefern 404 (existiert nicht); die zu große Zahl liefert 400 (nicht konvertierbar);
     * nichts wird gelöscht.
     */
    @Test
    void ungueltige_gericht_ids_werden_sauber_abgelehnt() throws Exception {
        createDish(admin, ownRestaurant, "Burger");

        deleteDish(admin, ownRestaurant, -1).andExpect(status().isNotFound());
        deleteDish(admin, ownRestaurant, 0).andExpect(status().isNotFound());
        deleteDish(admin, ownRestaurant, "99999999999999999999").andExpect(status().isBadRequest());

        assertEquals(1, dishRepo.count());
    }

    /**
     * Keine Seiteneffekte: Alle abgelehnten Löschanfragen (falsche Rolle, fremdes Restaurant, unbekanntes Gericht)
     * lassen die Speisekarte unverändert.
     *
     * <p>Erwartet: 403, 403, 404; die Speisekarte enthält weiterhin das Gericht.
     */
    @Test
    void nach_fehlgeschlagenem_loeschen_ist_die_speisekarte_unveraendert() throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");

        deleteDish(customer, ownRestaurant, dishId).andExpect(status().isForbidden());
        deleteDish(otherAdmin, ownRestaurant, dishId).andExpect(status().isForbidden());
        deleteDish(admin, ownRestaurant, 999999).andExpect(status().isNotFound());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(1)))
                .andExpect(jsonPath("$.gerichte[0].name").value("Burger"));
    }

    /**
     * Parametrisiert: Alle nicht berechtigten Konstellationen löschen nichts.
     *
     * <p>Erwartet: 403 Forbidden; das Gericht bleibt bestehen.
     */
    @ParameterizedTest(name = "Benutzer \"{0}\" darf das Gericht nicht löschen")
    @ValueSource(strings = {"customer", "otherAdmin"})
    void nicht_berechtigte_benutzer_koennen_nichts_loeschen(String who) throws Exception {
        long dishId = createDish(admin, ownRestaurant, "Burger");
        User actor = who.equals("customer") ? customer : otherAdmin;

        deleteDish(actor, ownRestaurant, dishId).andExpect(status().isForbidden());

        assertEquals(1, dishRepo.count());
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