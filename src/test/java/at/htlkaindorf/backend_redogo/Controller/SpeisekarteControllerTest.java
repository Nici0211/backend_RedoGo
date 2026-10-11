package at.htlkaindorf.backend_redogo.Controller;

import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * US28: Als Kunde möchte ich die Speisekarte von jedem Restaurant sehen.
 * Black-Box-Tests: geprüft wird nur der HTTP-Aufruf GET /restaurant/{id}/speisekarte.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SpeisekarteControllerTest {

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
    void setUpMockdaten() {
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

    // Die Speisekarte eines Restaurants wird als JSON geliefert
    @Test
    void speisekarte_antwortetMit200UndJson() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gerichte").isArray())
                .andExpect(jsonPath("$.menues").isArray());
    }

    // Die Speisekarte gehört eindeutig zu einem Restaurant (ID und Name)
    @Test
    void speisekarte_enthaeltRestaurantIdUndName() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(linz.getId()))
                .andExpect(jsonPath("$.restaurantName").value(LINZ_NAME));
    }

    // Alle Gerichte des Restaurants werden angezeigt
    @Test
    void speisekarte_liefertAlleGerichteDesRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gerichte.length()").value(2))
                .andExpect(jsonPath("$.gerichte[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.gerichte[1].name").value("Pommes Frites"));
    }

    // Ein Gericht hat Name, Beschreibung und Preis
    @Test
    void speisekarte_gerichtHatNameBeschreibungUndPreis() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(jsonPath("$.gerichte[0].name").value("Classic Cheeseburger"))
                .andExpect(jsonPath("$.gerichte[0].description").value("Rindfleisch, Cheddar und Salat"))
                .andExpect(jsonPath("$.gerichte[0].price").value(8.9));
    }

    // Alle Menüs des Restaurants werden angezeigt
    @Test
    void speisekarte_liefertAlleMenuesDesRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menues.length()").value(2))
                .andExpect(jsonPath("$.menues[0].name").value("Steirer-Menü"))
                .andExpect(jsonPath("$.menues[1].name").value("Kernöl-Menü"));
    }

    // Ein Menü hat Name, Beschreibung und Preis
    @Test
    void speisekarte_menueHatNameBeschreibungUndPreis() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", linz.getId()))
                .andExpect(jsonPath("$.menues[0].name").value("Burger-Menü"))
                .andExpect(jsonPath("$.menues[0].description").value("Cheeseburger, Pommes und Getränk"))
                .andExpect(jsonPath("$.menues[0].price").value(11.9));
    }

    // ═════════════════════════════ B) Jedes Restaurant hat seine eigene Speisekarte ═════════════════════════════

    // Restaurants haben unterschiedliche Gerichte und Menüs
    @Test
    void speisekarte_proRestaurantUnterschiedlich() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value(GRAZ_NAME))
                .andExpect(jsonPath("$.gerichte.length()").value(1))
                .andExpect(jsonPath("$.gerichte[0].name").value("Kürbiskernöl-Burger"))
                .andExpect(jsonPath("$.menues.length()").value(2));
    }

    // Gerichte und Menüs eines anderen Restaurants werden nicht angezeigt
    @Test
    void speisekarte_zeigtNurEintraegeDesEigenenRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", graz.getId()))
                .andExpect(jsonPath("$.gerichte[?(@.name=='Classic Cheeseburger')]").isEmpty())
                .andExpect(jsonPath("$.menues[?(@.name=='Burger-Menü')]").isEmpty());
    }

    // ═════════════════════════════ C) Sonderfälle und Fehler ═════════════════════════════

    // Ein Restaurant ohne Speisekarte liefert leere Listen (kein Fehler)
    @Test
    void speisekarte_restaurantOhneEintraege_leereListenMit200() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", wien.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value(WIEN_NAME))
                .andExpect(jsonPath("$.gerichte.length()").value(0))
                .andExpect(jsonPath("$.menues.length()").value(0));
    }

    // Ein nicht vorhandenes Restaurant wird mit 404 abgelehnt
    @Test
    void speisekarte_unbekannteRestaurantId_ist404() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", 999999L))
                .andExpect(status().isNotFound());
    }

    // Eine ungültige ID (keine Zahl) wird mit 400 abgelehnt
    @Test
    void speisekarte_ungueltigeRestaurantId_ist400() throws Exception {
        mockMvc.perform(get("/restaurant/{id}/speisekarte", "abc"))
                .andExpect(status().isBadRequest());
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

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
