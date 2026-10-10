package at.htlkaindorf.backend_redogo.Controller;

import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.LocationRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestaurantControllerTest {

    // ───────────────────────── Mockdaten (immer gleich) ─────────────────────────

    private static final String LINZ_NAME = "RedoGo Linz Landstraße";
    private static final String LINZ_STREET = "Landstraße 45";
    private static final long LINZ_PLZ = 4020L;
    private static final String LINZ_DESC = "Mitten in der Linzer Einkaufsstraße, mit Abholschalter.";

    private static final String GRAZ_NAME = "RedoGo Graz Hauptplatz";
    private static final String GRAZ_STREET = "Hauptplatz 1";
    private static final long GRAZ_PLZ = 8010L;
    private static final String GRAZ_DESC = "Unser erster Standort im Herzen von Graz - direkt neben dem Rathaus.";

    private static final String WIEN_NAME = "RedoGo Wien Naschmarkt";
    private static final String WIEN_STREET = "Naschmarkt 12";
    private static final long WIEN_PLZ = 1060L;
    private static final String WIEN_DESC = "Frisch und schnell direkt am Wiener Naschmarkt.";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private LocationRepository locationRepo;

    @BeforeEach
    void setUpMockdaten() {
        clearDatabase();
        save(LINZ_NAME, LINZ_PLZ, LINZ_STREET, LINZ_DESC);   // 1. Eintrag
        save(GRAZ_NAME, GRAZ_PLZ, GRAZ_STREET, GRAZ_DESC);   // 2. Eintrag
        save(WIEN_NAME, WIEN_PLZ, WIEN_STREET, WIEN_DESC);   // 3. Eintrag
    }

    // ═════════════════════════════ A) getAll ═════════════════════════════

    // Trello [1]: Alle Standorte werden mit Name, Adresse und Beschreibung angezeigt
    @Test
    void getAll_antwortetMit200UndJsonArray() throws Exception {
        mockMvc.perform(get("/restaurant/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    // Trello [1]: Alle Standorte werden mit Name, Adresse und Beschreibung angezeigt
    @Test
    void getAll_liefertAlleDreiStandorte() throws Exception {
        mockMvc.perform(get("/restaurant/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    // Trello [1]: Alle Standorte werden mit Name, Adresse und Beschreibung angezeigt
    @Test
    void getAll_standortHatNameStrassePlzUndBeschreibung() throws Exception {
        mockMvc.perform(get("/restaurant/getAll"))
                .andExpect(jsonPath("$[?(@.name=='" + GRAZ_NAME + "' && @.location.postalCode==" + GRAZ_PLZ
                        + " && @.location.streetName=='" + GRAZ_STREET
                        + "' && @.location.description=='" + GRAZ_DESC + "')]").exists());
    }

    // Trello [4]: Ohne vorhandene Standorte wird eine leere Liste angezeigt
    @Test
    void getAll_keineStandorte_leeresArrayMit200() throws Exception {
        clearDatabase();

        mockMvc.perform(get("/restaurant/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Trello [5]: Ein Standort ohne Adresse wird trotzdem angezeigt
    @Test
    void getAll_restaurantOhneLocation_locationIstNull() throws Exception {
        clearDatabase();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("RedoGo Innsbruck Maria-Theresien-Straße");
        restaurantRepo.save(restaurant);

        mockMvc.perform(get("/restaurant/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].location").isEmpty());
    }

    // ═════════════════════════════ B) getSortedBy ═════════════════════════════

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_frontendAufruf_nameAsc_alphabetisch() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value(GRAZ_NAME))
                .andExpect(jsonPath("$[1].name").value(LINZ_NAME))
                .andExpect(jsonPath("$[2].name").value(WIEN_NAME));
    }

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_ohneDirection_istAufsteigend() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(GRAZ_NAME))
                .andExpect(jsonPath("$[2].name").value(WIEN_NAME));
    }

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_nameDesc_absteigend() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(WIEN_NAME))
                .andExpect(jsonPath("$[1].name").value(LINZ_NAME))
                .andExpect(jsonPath("$[2].name").value(GRAZ_NAME));
    }

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_directionGrossgeschrieben_wirdAkzeptiert() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(WIEN_NAME))
                .andExpect(jsonPath("$[2].name").value(GRAZ_NAME));
    }

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_plzAsc_kleinstePlzZuerst() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "location.postalCode").param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(WIEN_NAME))
                .andExpect(jsonPath("$[0].location.postalCode").value(WIEN_PLZ))
                .andExpect(jsonPath("$[1].name").value(LINZ_NAME))
                .andExpect(jsonPath("$[2].name").value(GRAZ_NAME));
    }

    // Trello [2]: Die Standorte lassen sich sortieren (z. B. nach Name oder PLZ, auf- und absteigend)
    @Test
    void getSortedBy_idAsc_anlegereihenfolge() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "id").param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(LINZ_NAME))
                .andExpect(jsonPath("$[1].name").value(GRAZ_NAME))
                .andExpect(jsonPath("$[2].name").value(WIEN_NAME));
    }

    // Trello [4]: Ohne vorhandene Standorte wird eine leere Liste angezeigt
    @Test
    void getSortedBy_keineStandorte_leeresArrayMit200() throws Exception {
        clearDatabase();

        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ═════════════════════════════ C) Fehlerfälle ═════════════════════════════

    // Trello [3]: Eine ungültige Sortierung wird mit einer Fehlermeldung abgelehnt
    @Test
    void getSortedBy_ohneByParameter_ist400() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy"))
                .andExpect(status().isBadRequest());
    }

    // Trello [3]: Eine ungültige Sortierung wird mit einer Fehlermeldung abgelehnt
    @Test
    void getSortedBy_unbekanntesFeld_ist400() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "gibtEsNicht"))
                .andExpect(status().isBadRequest());
    }

    // Trello [3]: Eine ungültige Sortierung wird mit einer Fehlermeldung abgelehnt
    @Test
    void getSortedBy_ungueltigeRichtung_ist400() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "seitwaerts"))
                .andExpect(status().isBadRequest());
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    private void save(String name, Long postalCode, String streetName, String description) {
        Location location = new Location();
        location.setPostalCode(postalCode);
        location.setStreetName(streetName);
        location.setDescription(description);
        location = locationRepo.save(location);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(location);
        restaurantRepo.save(restaurant);
    }

    private void clearDatabase() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        restaurants.forEach(restaurant -> restaurant.setLocation(null));
        restaurantRepo.saveAll(restaurants);

        List<Location> locations = locationRepo.findAll();
        locations.forEach(location -> location.setRestaurant(null));
        locationRepo.saveAll(locations);

        restaurantRepo.deleteAll();
        locationRepo.deleteAll();
    }
}