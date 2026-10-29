package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US09: Als Restaurantinhaber möchte ich neue Gerichte zur Speisekarte meines Restaurants hinzufügen.
 * Black-Box-Tests über POST /api/admin/{adminId}/restaurants/{restaurantId}/dishes.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminDishControllerTest {

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

    private String url(User user, Restaurant restaurant) {
        return "/api/admin/" + user.getId() + "/restaurants/" + restaurant.getId() + "/dishes";
    }

    private static String body(String name, String description, String price) {
        return """
                {"name": %s, "description": %s, "price": %s}
                """.formatted(name == null ? "null" : "\"" + name + "\"",
                description == null ? "null" : "\"" + description + "\"", price);
    }

    /**
     * Happy Path: Ein Admin legt ein gültiges Gericht in seinem eigenen Restaurant an.
     *
     * <p>Erwartet: 201 Created; Antwort enthält ID, Name, Beschreibung, Preis und available=true; das Gericht ist in der Datenbank gespeichert.
     */
    @Test
    void admin_kann_gericht_zum_eigenen_restaurant_hinzufuegen() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Käsespätzle", "Mit Röstzwiebeln", "9.80")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Käsespätzle"))
                .andExpect(jsonPath("$.description").value("Mit Röstzwiebeln"))
                .andExpect(jsonPath("$.price").value(9.80))
                .andExpect(jsonPath("$.available").value(true));

        assertEquals(1, dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).size());
    }

    /**
     * Zusammenspiel mit der Speisekarte (US28): Ein angelegtes Gericht muss für Kunden sichtbar sein.
     *
     * <p>Erwartet: POST liefert 201; GET /restaurant/{id}/speisekarte enthält genau dieses Gericht.
     */
    @Test
    void neues_gericht_erscheint_auf_der_speisekarte() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Gulasch", null, "11.50")))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(1)))
                .andExpect(jsonPath("$.gerichte[0].name").value("Gulasch"));
    }

    /**
     * Feld available=false wird beim Anlegen berücksichtigt.
     *
     * <p>Erwartet: 201 Created; Antwort enthält available=false.
     */
    @Test
    void gericht_kann_als_nicht_verfuegbar_angelegt_werden() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Saisonsuppe\",\"price\":4.5,\"available\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(false));
    }

    /**
     * Rollenprüfung: Nur Benutzer mit Rolle ADMIN dürfen Gerichte anlegen.
     *
     * <p>Erwartet: 403 Forbidden; es wird nichts gespeichert (0 Gerichte in der Datenbank).
     */
    @Test
    void normaler_user_darf_keine_gerichte_anlegen() throws Exception {
        mockMvc.perform(post(url(customer, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isForbidden());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Besitzprüfung: Ein Admin darf nur Gerichte in Restaurants anlegen, deren Inhaber er ist.
     *
     * <p>Erwartet: 403 Forbidden; es wird nichts gespeichert.
     */
    @Test
    void admin_darf_nicht_in_fremdem_restaurant_anlegen() throws Exception {
        mockMvc.perform(post(url(admin, foreignRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isForbidden());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Die Admin-ID im Pfad gehört zu keinem Benutzer.
     *
     * <p>Erwartet: 404 Not Found.
     */
    @Test
    void unbekannter_benutzer_liefert_404() throws Exception {
        mockMvc.perform(post("/api/admin/999999/restaurants/" + ownRestaurant.getId() + "/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound());
    }

    /**
     * Die Restaurant-ID im Pfad gehört zu keinem Restaurant.
     *
     * <p>Erwartet: 404 Not Found.
     */
    @Test
    void unbekanntes_restaurant_liefert_404() throws Exception {
        mockMvc.perform(post("/api/admin/" + admin.getId() + "/restaurants/999999/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound());
    }

    /**
     * Bean Validation (@NotBlank): Ein Name, der nur aus Leerzeichen besteht, ist ungültig.
     *
     * <p>Erwartet: 400 Bad Request mit der Meldung "Bitte einen Namen für das Gericht angeben".
     */
    @Test
    void leerer_name_wird_abgelehnt() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("   ", null, "8.90")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Namen für das Gericht angeben"));
    }

    /**
     * Bean Validation beim Preis: null, negative Werte und 0 sind ungültig.
     *
     * <p>Erwartet: Jeweils 400 Bad Request; nichts wird gespeichert.
     */
    @Test
    void preis_null_oder_negativ_wird_abgelehnt() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "null")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "-1")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "0")))
                .andExpect(status().isBadRequest());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@Digits): Der Preis darf höchstens 2 Nachkommastellen haben (8.999).
     *
     * <p>Erwartet: 400 Bad Request.
     */
    @Test
    void preis_mit_mehr_als_zwei_nachkommastellen_wird_abgelehnt() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.999")))
                .andExpect(status().isBadRequest());
    }

    /**
     * Eindeutigkeit: Ein Gericht mit gleichem Namen (Groß-/Kleinschreibung egal) darf es pro Restaurant nur einmal geben.
     *
     * <p>Erwartet: Erstes Anlegen 201, zweites Anlegen ("burger" statt "Burger") 409 Conflict; es bleibt bei einem Gericht.
     */
    @Test
    void doppelter_name_im_selben_restaurant_liefert_409() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isCreated());

        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("burger", null, "9.90")))
                .andExpect(status().isConflict());

        assertEquals(1, dishRepo.count());
    }

    /**
     * Restaurantliste im Admin-Bereich: Es werden nur Restaurants des jeweiligen Admins geliefert.
     *
     * <p>Erwartet: 200 OK; Liste enthält genau ein Restaurant ("Eigenes Restaurant"), nicht das fremde.
     */
    @Test
    void admin_sieht_nur_seine_eigenen_restaurants() throws Exception {
        mockMvc.perform(get("/api/admin/" + admin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Eigenes Restaurant"));
    }

    /**
     * Rollenprüfung bei der Restaurantliste: Normale Kunden haben keinen Zugriff.
     *
     * <p>Erwartet: 403 Forbidden.
     */
    @Test
    void normaler_user_darf_restaurantliste_des_admin_bereichs_nicht_sehen() throws Exception {
        mockMvc.perform(get("/api/admin/" + customer.getId() + "/restaurants"))
                .andExpect(status().isForbidden());
    }

    // =====================================================================
    // Zusätzliche Black-Box-Tests: Validierung, 404-Fälle, available, Randwerte
    // =====================================================================

    private org.springframework.test.web.servlet.ResultActions postDish(User user, Restaurant restaurant, String json)
            throws Exception {
        return mockMvc.perform(post(url(user, restaurant))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    // ---------- Validierung: Name ----------

    /**
     * Bean Validation (@NotBlank): Leerer String, Leerzeichen und Tab als Name sind ungültig (parametrisiert).
     *
     * <p>Erwartet: 400 Bad Request, status=400 und Meldung "Bitte einen Namen für das Gericht angeben"; nichts wird gespeichert.
     */
    @ParameterizedTest(name = "Name \"{0}\" wird mit 400 abgelehnt")
    @ValueSource(strings = {"", " ", "   ", "\\t"})
    void leerer_oder_blanker_name_liefert_400(String name) throws Exception {
        postDish(admin, ownRestaurant, body(name, null, "8.90"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Bitte einen Namen für das Gericht angeben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@NotBlank): Name ist im JSON explizit null.
     *
     * <p>Erwartet: 400 Bad Request mit Namens-Meldung; nichts wird gespeichert.
     */
    @Test
    void name_null_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body(null, null, "8.90"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Namen für das Gericht angeben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@NotBlank): Das Feld name fehlt komplett im JSON.
     *
     * <p>Erwartet: 400 Bad Request mit Namens-Meldung; nichts wird gespeichert.
     */
    @Test
    void name_fehlt_im_json_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, "{\"price\": 8.90}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Namen für das Gericht angeben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Grenzwert (@Size max=100): Ein Name mit 101 Zeichen ist zu lang.
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Der Name darf höchstens 100 Zeichen lang sein".
     */
    @Test
    void name_mit_101_zeichen_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body("a".repeat(101), null, "8.90"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Der Name darf höchstens 100 Zeichen lang sein"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Grenzwert (@Size max=100): Ein Name mit genau 100 Zeichen ist noch erlaubt.
     *
     * <p>Erwartet: 201 Created; Name wird vollständig zurückgegeben.
     */
    @Test
    void name_mit_genau_100_zeichen_wird_akzeptiert() throws Exception {
        postDish(admin, ownRestaurant, body("a".repeat(100), null, "8.90"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a".repeat(100)));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Geschäftslogik: Der Name wird vor dem Speichern getrimmt.
     *
     * <p>Erwartet: 201 Created; Name in der Antwort ist "Schnitzel" ohne Leerzeichen.
     */
    @Test
    void name_wird_ohne_fuehrende_und_nachfolgende_leerzeichen_gespeichert() throws Exception {
        postDish(admin, ownRestaurant, body("   Schnitzel   ", null, "12.00"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Schnitzel"));
    }

    // ---------- Validierung: Preis ----------

    /**
     * Grenzwerte (@DecimalMin 0.01): 0, 0.00, 0.001, 0.009, -0.01, -1, -100 sind ungültig (parametrisiert).
     *
     * <p>Erwartet: Jeweils 400 Bad Request; nichts wird gespeichert.
     */
    @ParameterizedTest(name = "Preis {0} wird mit 400 abgelehnt")
    @ValueSource(strings = {"0", "0.00", "0.001", "0.009", "-0.01", "-1", "-100"})
    void preis_kleiner_gleich_null_liefert_400(String price) throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, price))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@NotNull): Preis ist im JSON explizit null.
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Bitte einen Preis angeben".
     */
    @Test
    void preis_null_liefert_400_mit_meldung() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "null"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Preis angeben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@NotNull): Das Feld price fehlt im JSON.
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Bitte einen Preis angeben".
     */
    @Test
    void preis_fehlt_im_json_liefert_400_mit_meldung() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\": \"Burger\"}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Preis angeben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Fehlermeldung bei negativem Preis (-5).
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Der Preis muss größer als 0 sein".
     */
    @Test
    void negativer_preis_liefert_meldung_groesser_als_0() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "-5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Der Preis muss größer als 0 sein"));
    }

    /**
     * Bean Validation (@Digits fraction=2): Preis 8.999 hat zu viele Nachkommastellen.
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Der Preis darf höchstens 2 Nachkommastellen haben".
     */
    @Test
    void preis_mit_mehr_als_zwei_nachkommastellen_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "8.999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Der Preis darf höchstens 2 Nachkommastellen haben"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Bean Validation (@Digits integer=6): Preis 1000000.00 hat 7 Vorkommastellen.
     *
     * <p>Erwartet: 400 Bad Request; nichts wird gespeichert.
     */
    @Test
    void preis_mit_mehr_als_sechs_vorkommastellen_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "1000000.00"))
                .andExpect(status().isBadRequest());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Falscher Datentyp: Der Preis wird als Text ("abc") statt als Zahl geschickt.
     *
     * <p>Erwartet: 400 Bad Request (JSON kann nicht gelesen werden); nichts wird gespeichert.
     */
    @Test
    void preis_als_text_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "\"abc\""))
                .andExpect(status().isBadRequest());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Gültige Preise inkl. Grenzwerte: kleinster Preis 0.01 und größter Preis 999999.99 sowie normale Werte (parametrisiert).
     *
     * <p>Erwartet: 201 Created; Preis in der Antwort entspricht dem gesendeten Wert.
     */
    @ParameterizedTest(name = "Preis {0} wird akzeptiert")
    @ValueSource(strings = {"0.01", "1", "8.9", "8.90", "999999.99"})
    void gueltige_preise_inklusive_grenzwerte_werden_akzeptiert(String price) throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, price))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(new java.math.BigDecimal(price).doubleValue()));

        assertEquals(1, dishRepo.count());
    }

    // ---------- Validierung: Beschreibung ----------

    /**
     * Grenzwert (@Size max=500): Eine Beschreibung mit 501 Zeichen ist zu lang.
     *
     * <p>Erwartet: 400 Bad Request mit Meldung "Die Beschreibung darf höchstens 500 Zeichen lang sein".
     */
    @Test
    void beschreibung_mit_501_zeichen_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", "b".repeat(501), "8.90"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Die Beschreibung darf höchstens 500 Zeichen lang sein"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Grenzwert (@Size max=500): Eine Beschreibung mit genau 500 Zeichen ist erlaubt und muss auch in der Datenbank Platz haben (Spaltenlänge 500).
     *
     * <p>Erwartet: 201 Created; das Gericht wird gespeichert.
     */
    @Test
    void beschreibung_mit_genau_500_zeichen_wird_akzeptiert() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", "b".repeat(500), "8.90"))
                .andExpect(status().isCreated());

        assertEquals(1, dishRepo.count());
    }

    /**
     * Die Beschreibung ist optional: Das Feld fehlt im JSON.
     *
     * <p>Erwartet: 201 Created; description in der Antwort ist null.
     */
    @Test
    void fehlende_beschreibung_wird_als_null_zurueckgegeben() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Burger\",\"price\":8.90}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(nullValue()));
    }

    /**
     * Geschäftslogik: Eine Beschreibung, die nur aus Leerzeichen besteht, wird zu null normalisiert.
     *
     * <p>Erwartet: 201 Created; description in der Antwort ist null.
     */
    @Test
    void leere_beschreibung_wird_als_null_zurueckgegeben() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", "   ", "8.90"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(nullValue()));
    }

    // ---------- Feld "available" ----------

    /**
     * Standardwert: Fehlt available im JSON, ist das Gericht sofort verfügbar.
     *
     * <p>Erwartet: 201 Created; available=true in Antwort und in der Datenbank.
     */
    @Test
    void ohne_available_ist_das_gericht_standardmaessig_verfuegbar() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Gulasch\",\"price\":11.50}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(true));

        assertTrue(dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).get(0).isAvailable());
    }

    /**
     * Standardwert: available ist explizit null und wird wie "nicht angegeben" behandelt.
     *
     * <p>Erwartet: 201 Created; available=true.
     */
    @Test
    void available_null_ist_standardmaessig_verfuegbar() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Gulasch\",\"price\":11.50,\"available\":null}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(true));
    }

    /**
     * available=true wird unverändert übernommen.
     *
     * <p>Erwartet: 201 Created; available=true.
     */
    @Test
    void available_true_wird_uebernommen() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Gulasch\",\"price\":11.50,\"available\":true}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(true));
    }

    /**
     * available=false wird nicht nur zurückgegeben, sondern auch in der Datenbank gespeichert.
     *
     * <p>Erwartet: 201 Created; Antwort und gespeichertes Gericht haben available=false.
     */
    @Test
    void available_false_wird_in_der_datenbank_gespeichert() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Saisonsuppe\",\"price\":4.5,\"available\":false}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(false));

        assertFalse(dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).get(0).isAvailable());
    }

    /**
     * Falscher Datentyp: available wird als Text ("vielleicht") statt als true/false geschickt.
     *
     * <p>Erwartet: 400 Bad Request; nichts wird gespeichert.
     */
    @Test
    void available_als_text_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\":\"Gulasch\",\"price\":11.50,\"available\":\"vielleicht\"}")
                .andExpect(status().isBadRequest());

        assertEquals(0, dishRepo.count());
    }

    // ---------- 404: Restaurant bzw. Benutzer nicht gefunden ----------

    /**
     * Restaurant nicht gefunden: Der Admin existiert, aber die Restaurant-ID nicht.
     *
     * <p>Erwartet: 404 Not Found, status=404 und Meldung "Restaurant mit der ID 999999 wurde nicht gefunden" (Restaurant-Meldung, nicht Benutzer-Meldung); nichts wird gespeichert.
     */
    @Test
    void unbekanntes_restaurant_liefert_404_mit_restaurant_meldung() throws Exception {
        mockMvc.perform(post("/api/admin/" + admin.getId() + "/restaurants/999999/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Restaurant mit der ID 999999 wurde nicht gefunden"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Benutzer nicht gefunden: Das Restaurant existiert, aber die Admin-ID nicht.
     *
     * <p>Erwartet: 404 Not Found mit Meldung "Benutzer mit der ID 999999 wurde nicht gefunden"; nichts wird gespeichert.
     */
    @Test
    void unbekannter_benutzer_liefert_404_mit_benutzer_meldung() throws Exception {
        mockMvc.perform(post("/api/admin/999999/restaurants/" + ownRestaurant.getId() + "/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Benutzer mit der ID 999999 wurde nicht gefunden"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Reihenfolge der Prüfungen: Fehlen Benutzer und Restaurant, wird zuerst der Benutzer geprüft.
     *
     * <p>Erwartet: 404 Not Found mit Benutzer-Meldung.
     */
    @Test
    void unbekannter_benutzer_und_unbekanntes_restaurant_liefert_404_fuer_den_benutzer() throws Exception {
        mockMvc.perform(post("/api/admin/999998/restaurants/999999/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Benutzer mit der ID 999998 wurde nicht gefunden"));
    }

    /**
     * Reihenfolge der Prüfungen: Die Rollenprüfung kommt vor der Restaurant-Suche, damit Nicht-Admins nicht erfahren, ob ein Restaurant existiert.
     *
     * <p>Erwartet: 403 Forbidden mit Meldung "Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten" (nicht 404).
     */
    @Test
    void normaler_user_bekommt_bei_unbekanntem_restaurant_403_statt_404() throws Exception {
        mockMvc.perform(post("/api/admin/" + customer.getId() + "/restaurants/999999/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten"));
    }

    /**
     * Restaurantliste für eine nicht existierende Admin-ID.
     *
     * <p>Erwartet: 404 Not Found mit Benutzer-Meldung.
     */
    @Test
    void restaurantliste_fuer_unbekannten_benutzer_liefert_404() throws Exception {
        mockMvc.perform(get("/api/admin/999999/restaurants"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Benutzer mit der ID 999999 wurde nicht gefunden"));
    }

    // ---------- 403: Berechtigungen ----------

    /**
     * Besitzprüfung: Admin versucht, ins Restaurant eines anderen Admins zu schreiben.
     *
     * <p>Erwartet: 403 Forbidden mit Meldung "Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten"; nichts wird gespeichert.
     */
    @Test
    void fremdes_restaurant_liefert_403_mit_meldung() throws Exception {
        postDish(admin, foreignRestaurant, body("Burger", null, "8.90"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten"));

        assertEquals(0, dishRepo.count());
    }

    /**
     * Randfall: Das Restaurant hat gar keinen Inhaber (owner = null); niemand darf es bearbeiten.
     *
     * <p>Erwartet: 403 Forbidden; nichts wird gespeichert.
     */
    @Test
    void restaurant_ohne_inhaber_liefert_403() throws Exception {
        Restaurant ownerless = saveRestaurant("Herrenloses Restaurant", null);

        postDish(admin, ownerless, body("Burger", null, "8.90"))
                .andExpect(status().isForbidden());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Rollenprüfung: Ein Kunde (Rolle USER) versucht ein Gericht anzulegen.
     *
     * <p>Erwartet: 403 Forbidden, status=403 und Meldung "Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten".
     */
    @Test
    void normaler_user_bekommt_403_mit_meldung() throws Exception {
        postDish(customer, ownRestaurant, body("Burger", null, "8.90"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten"));
    }

    // ---------- 409: Duplikate ----------

    /**
     * Eindeutigkeit: Der Duplikat-Check trimmt den Namen und ignoriert Groß-/Kleinschreibung ("  BURGER  " = "Burger").
     *
     * <p>Erwartet: 409 Conflict, status=409 und Meldung mit getrimmtem Namen; es bleibt bei einem Gericht.
     */
    @Test
    void doppelter_name_wird_auch_mit_leerzeichen_und_grossschreibung_erkannt() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "8.90")).andExpect(status().isCreated());

        postDish(admin, ownRestaurant, body("  BURGER  ", null, "9.90"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Ein Gericht mit dem Namen \"BURGER\" gibt es in diesem Restaurant bereits"));

        assertEquals(1, dishRepo.count());
    }

    /**
     * Eindeutigkeit gilt nur pro Restaurant: Zwei Restaurants dürfen ein Gericht mit demselben Namen haben.
     *
     * <p>Erwartet: Beide Anfragen 201 Created; 2 Gerichte in der Datenbank.
     */
    @Test
    void gleicher_name_in_anderem_restaurant_ist_erlaubt() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "8.90")).andExpect(status().isCreated());
        postDish(otherAdmin, foreignRestaurant, body("Burger", null, "9.90")).andExpect(status().isCreated());

        assertEquals(2, dishRepo.count());
    }

    /**
     * Mehrere Gerichte mit unterschiedlichen Namen im selben Restaurant.
     *
     * <p>Erwartet: Beide Anfragen 201 Created; 2 Gerichte im Restaurant.
     */
    @Test
    void verschiedene_namen_im_selben_restaurant_sind_erlaubt() throws Exception {
        postDish(admin, ownRestaurant, body("Burger", null, "8.90")).andExpect(status().isCreated());
        postDish(admin, ownRestaurant, body("Pizza", null, "9.90")).andExpect(status().isCreated());

        assertEquals(2, dishRepo.findByRestaurantIdOrderByIdAsc(ownRestaurant.getId()).size());
    }

    // ---------- Fehlerhafte Anfragen ----------

    /**
     * Fehlerhafte Anfrage: Das JSON ist syntaktisch unvollständig.
     *
     * <p>Erwartet: 400 Bad Request; nichts wird gespeichert.
     */
    @Test
    void kaputtes_json_liefert_400() throws Exception {
        postDish(admin, ownRestaurant, "{\"name\": \"Burger\", ")
                .andExpect(status().isBadRequest());

        assertEquals(0, dishRepo.count());
    }

    /**
     * Fehlerhafte Anfrage: Der POST hat gar keinen Body.
     *
     * <p>Erwartet: 400 Bad Request.
     */
    @Test
    void fehlender_body_liefert_400() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Fehlerhafte Anfrage: Der Body wird als text/plain statt als application/json gesendet.
     *
     * <p>Erwartet: 415 Unsupported Media Type.
     */
    @Test
    void falscher_content_type_liefert_415() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isUnsupportedMediaType());
    }

    /**
     * Fehlerhafte Anfrage: adminId bzw. restaurantId im Pfad sind keine Zahlen ("abc").
     *
     * <p>Erwartet: Jeweils 400 Bad Request.
     */
    @Test
    void nicht_numerische_ids_im_pfad_liefern_400() throws Exception {
        mockMvc.perform(post("/api/admin/abc/restaurants/" + ownRestaurant.getId() + "/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/admin/" + admin.getId() + "/restaurants/abc/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isBadRequest());
    }

    // ---------- Restaurantliste ----------

    /**
     * Restaurantliste: Ein Admin, dem noch kein Restaurant gehört.
     *
     * <p>Erwartet: 200 OK mit leerer Liste [].
     */
    @Test
    void admin_ohne_restaurants_bekommt_leere_liste() throws Exception {
        User newAdmin = saveUser("neu@test.at", Role.ADMIN);

        mockMvc.perform(get("/api/admin/" + newAdmin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Restaurantliste ist nach Name aufsteigend sortiert (Adler, Eigenes Restaurant, Zum Löwen).
     *
     * <p>Erwartet: 200 OK; 3 Einträge in alphabetischer Reihenfolge.
     */
    @Test
    void restaurantliste_ist_alphabetisch_nach_name_sortiert() throws Exception {
        saveRestaurant("Zum Löwen", admin);
        saveRestaurant("Adler", admin);

        mockMvc.perform(get("/api/admin/" + admin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Adler"))
                .andExpect(jsonPath("$[1].name").value("Eigenes Restaurant"))
                .andExpect(jsonPath("$[2].name").value("Zum Löwen"));
    }

    /**
     * Restaurantliste enthält die ID, die das Frontend für den POST-Aufruf braucht.
     *
     * <p>Erwartet: 200 OK; id des ersten Eintrags entspricht dem gespeicherten Restaurant.
     */
    @Test
    void restaurantliste_enthaelt_id_des_restaurants() throws Exception {
        mockMvc.perform(get("/api/admin/" + admin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ownRestaurant.getId()));
    }

    /**
     * Datentrennung: Der zweite Admin sieht nur sein eigenes Restaurant, nicht das des ersten.
     *
     * <p>Erwartet: 200 OK; genau ein Eintrag "Fremdes Restaurant".
     */
    @Test
    void restaurantliste_eines_anderen_admins_enthaelt_nur_dessen_restaurants() throws Exception {
        mockMvc.perform(get("/api/admin/" + otherAdmin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Fremdes Restaurant"));
    }

    /**
     * Keine Seiteneffekte: Abgelehnte Anfragen (ungültiger Name, Preis 0, falsche Rolle) dürfen nichts speichern.
     *
     * <p>Erwartet: Alle drei Anfragen scheitern (400, 400, 403); die Speisekarte des Restaurants ist weiterhin leer.
     */
    @Test
    void nach_fehlgeschlagener_anfrage_ist_die_speisekarte_unveraendert_leer() throws Exception {
        postDish(admin, ownRestaurant, body("", null, "8.90")).andExpect(status().isBadRequest());
        postDish(admin, ownRestaurant, body("Burger", null, "0")).andExpect(status().isBadRequest());
        postDish(customer, ownRestaurant, body("Burger", null, "8.90")).andExpect(status().isForbidden());

        mockMvc.perform(get("/restaurant/" + ownRestaurant.getId() + "/speisekarte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte", hasSize(0)));
    }

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
