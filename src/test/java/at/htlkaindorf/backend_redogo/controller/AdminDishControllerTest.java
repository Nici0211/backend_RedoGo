package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void gericht_kann_als_nicht_verfuegbar_angelegt_werden() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Saisonsuppe\",\"price\":4.5,\"available\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void normaler_user_darf_keine_gerichte_anlegen() throws Exception {
        mockMvc.perform(post(url(customer, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isForbidden());

        assertEquals(0, dishRepo.count());
    }

    @Test
    void admin_darf_nicht_in_fremdem_restaurant_anlegen() throws Exception {
        mockMvc.perform(post(url(admin, foreignRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isForbidden());

        assertEquals(0, dishRepo.count());
    }

    @Test
    void unbekannter_benutzer_liefert_404() throws Exception {
        mockMvc.perform(post("/api/admin/999999/restaurants/" + ownRestaurant.getId() + "/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound());
    }

    @Test
    void unbekanntes_restaurant_liefert_404() throws Exception {
        mockMvc.perform(post("/api/admin/" + admin.getId() + "/restaurants/999999/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.90")))
                .andExpect(status().isNotFound());
    }

    @Test
    void leerer_name_wird_abgelehnt() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("   ", null, "8.90")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bitte einen Namen für das Gericht angeben"));
    }

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

    @Test
    void preis_mit_mehr_als_zwei_nachkommastellen_wird_abgelehnt() throws Exception {
        mockMvc.perform(post(url(admin, ownRestaurant))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Burger", null, "8.999")))
                .andExpect(status().isBadRequest());
    }

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

    @Test
    void admin_sieht_nur_seine_eigenen_restaurants() throws Exception {
        mockMvc.perform(get("/api/admin/" + admin.getId() + "/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Eigenes Restaurant"));
    }

    @Test
    void normaler_user_darf_restaurantliste_des_admin_bereichs_nicht_sehen() throws Exception {
        mockMvc.perform(get("/api/admin/" + customer.getId() + "/restaurants"))
                .andExpect(status().isForbidden());
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
