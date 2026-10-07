package at.htlkaindorf.backend_redogo.Controller;

import at.htlkaindorf.backend_redogo.beans.Location;
import at.htlkaindorf.backend_redogo.beans.Restaurant;
import at.htlkaindorf.backend_redogo.repository.LocationRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test of RestaurantController#getSortedBy against the REAL stack
 * (real service, real mapper, real repository, real PostgreSQL database).
 * No mocks are used anywhere in this class - the whole context is booted and
 * every request runs through the full HTTP layer.
 *
 * Testable elements of the route GET /restaurant/getSortedBy:
 *  1. the route exists and answers with HTTP 200 and a JSON array
 *  2. the response carries every restaurant that is stored (length)
 *  3. every element has the DTO shape (name + location.postalCode/streetName)
 *  4. no direction given -> sorted ascending by the requested field
 *  5. direction=desc -> exactly the reverse order of ascending
 *  6. direction is case-insensitive (DESC)
 *  7. sorting by another field (location.postalCode) really uses that field
 *  8. sorting by id -> insertion order (differs from the name order)
 *  9. missing parameter "by" -> HTTP 400
 * 10. unknown sort field -> HTTP 400 (not 500)
 * 11. invalid direction -> HTTP 400
 * 12. empty database -> HTTP 200 with an empty array
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestaurantGetSortedByControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private LocationRepository locationRepo;

    @BeforeEach
    void setUpFixture() {
        clearDatabase();
        // insertion order is deliberately NOT the alphabetical order:
        // ids grow Zulu -> Alpha -> Mid
        save("Zulu", 1000L, "Z Street 1");
        save("Alpha", 3000L, "A Street 2");
        save("Mid", 2000L, "M Street 3");
    }

    // 1 + 2: route exists, answers 200 with a JSON array holding all restaurants
    @Test
    void getSortedBy_returnsAllRestaurantsAsJsonArray() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    // 3: DTO structure of a single element
    @Test
    void getSortedBy_returnsRestaurantDtoStructure() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alpha"))
                .andExpect(jsonPath("$[0].location.postalCode").value(3000))
                .andExpect(jsonPath("$[0].location.streetName").value("A Street 2"))
                .andExpect(jsonPath("$[0].location.description").exists());
    }

    // 4: default direction is ascending
    @Test
    void getSortedBy_byNameWithoutDirection_sortsAscending() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alpha"))
                .andExpect(jsonPath("$[1].name").value("Mid"))
                .andExpect(jsonPath("$[2].name").value("Zulu"));
    }

    // 4 (explicit asc)
    @Test
    void getSortedBy_byNameAscending_sortsAscending() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alpha"))
                .andExpect(jsonPath("$[1].name").value("Mid"))
                .andExpect(jsonPath("$[2].name").value("Zulu"));
    }

    // 5: descending is the exact reverse
    @Test
    void getSortedBy_byNameDescending_sortsDescending() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Zulu"))
                .andExpect(jsonPath("$[1].name").value("Mid"))
                .andExpect(jsonPath("$[2].name").value("Alpha"));
    }

    // 6: direction is case-insensitive
    @Test
    void getSortedBy_directionUppercase_isAccepted() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Zulu"))
                .andExpect(jsonPath("$[2].name").value("Alpha"));
    }

    // 7: sorting on a nested field uses that field, not the name
    @Test
    void getSortedBy_byPostalCode_sortsByPostalCode() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "location.postalCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Zulu"))
                .andExpect(jsonPath("$[0].location.postalCode").value(1000))
                .andExpect(jsonPath("$[1].name").value("Mid"))
                .andExpect(jsonPath("$[1].location.postalCode").value(2000))
                .andExpect(jsonPath("$[2].name").value("Alpha"))
                .andExpect(jsonPath("$[2].location.postalCode").value(3000));
    }

    // 8: id order = insertion order, which is different from the name order
    @Test
    void getSortedBy_byIdAscending_sortsById() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Zulu"))
                .andExpect(jsonPath("$[1].name").value("Alpha"))
                .andExpect(jsonPath("$[2].name").value("Mid"));
    }

    // 9: required parameter "by" is missing
    @Test
    void getSortedBy_missingByParameter_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy"))
                .andExpect(status().isBadRequest());
    }

    // 10: field that does not exist on the entity
    @Test
    void getSortedBy_unknownSortField_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "noSuchField"))
                .andExpect(status().isBadRequest());
    }

    // 11: direction that is neither asc nor desc
    @Test
    void getSortedBy_invalidDirection_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name").param("direction", "sideways"))
                .andExpect(status().isBadRequest());
    }

    // 12: no data at all -> empty list, still a 200
    @Test
    void getSortedBy_noRestaurants_returnsEmptyList() throws Exception {
        clearDatabase();

        mockMvc.perform(get("/restaurant/getSortedBy").param("by", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    private void save(String name, Long postalCode, String streetName) {
        Location location = new Location();
        location.setPostalCode(postalCode);
        location.setStreetName(streetName);
        location.setDescription("Test location of " + name);
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
