package at.htlkaindorf.backend_redogo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.htlkaindorf.backend_redogo.dto.CreateDishRequestDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.exception.GlobalExceptionHandler;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import at.htlkaindorf.backend_redogo.service.AdminDishService;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(
        classes = {AdminDishController.class, AdminDishService.class, GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class AdminDishControllerDiffblueTest {
    @Autowired
    private AdminDishController adminDishController;

    @MockitoBean
    private DishRepository dishRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @MockitoBean
    private RestaurantMapper restaurantMapper;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @MockitoBean
    private UserRepository userRepository;

    /**
     * Test {@link AdminDishController#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Role is {@code ADMIN}.
     *   <li>When one.
     *   <li>Then status {@link StatusResultMatchers#isOk()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName(
            "Test getOwnRestaurants(Long); given User() Role is 'ADMIN'; when one; then status isOk()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"java.util.List AdminDishController.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_givenUserRoleIsAdmin_whenOne_thenStatusIsOk() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.ADMIN);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/admin/{adminId}/restaurants", 1L);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findByOwnerIdOrderByNameAsc(Mockito.<Long>any())).thenReturn(List.of());

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    /**
     * Test {@link AdminDishController#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Role is {@code USER}.
     *   <li>When one.
     *   <li>Then status {@link StatusResultMatchers#isForbidden()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName(
            "Test getOwnRestaurants(Long); given User() Role is 'USER'; when one; then status isForbidden()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"java.util.List AdminDishController.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_givenUserRoleIsUser_whenOne_thenStatusIsForbidden() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/admin/{adminId}/restaurants", 1L);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":403,\"message\":\"Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten\"}"));
    }

    /**
     * Test {@link AdminDishController#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Then status {@link StatusResultMatchers#isNotFound()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName("Test getOwnRestaurants(Long); then status isNotFound()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"java.util.List AdminDishController.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_thenStatusIsNotFound() throws Exception {
        // Arrange
        Optional<User> emptyResult = Optional.empty();

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/admin/{adminId}/restaurants", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":404,\"message\":\"Benutzer mit der ID 1 wurde nicht gefunden\"}"));
    }

    /**
     * Test {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto AdminDishController.addDish(Long, Long, CreateDishRequestDto)"
    })
    void testAddDish() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        CreateDishRequestDto createDishRequestDto = new CreateDishRequestDto();
        createDishRequestDto.setAvailable(true);
        createDishRequestDto.setDescription("The characteristics of someone or something");
        createDishRequestDto.setName("Bella");
        createDishRequestDto.setPrice(new BigDecimal("2.3"));
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(createDishRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(
                                "/api/admin/{adminId}/restaurants/{restaurantId}/dishes", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":403,\"message\":\"Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten\"}"));
    }

    /**
     * Test {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto AdminDishController.addDish(Long, Long, CreateDishRequestDto)"
    })
    void testAddDish2() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(2L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.ADMIN);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("Hausnummer");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("Ort");
        owner.setPasswort("Passwort");
        owner.setPlz("Plz");
        owner.setRole(Role.USER);
        owner.setStrasse("Strasse");
        owner.setTelefon("Telefon");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(restaurant);
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("Hausnummer");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("Ort");
        owner2.setPasswort("Passwort");
        owner2.setPlz("Plz");
        owner2.setRole(Role.USER);
        owner2.setStrasse("Strasse");
        owner2.setTelefon("Telefon");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);
        Optional<Restaurant> ofResult2 = Optional.of(restaurant2);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant3);
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.USER);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        CreateDishRequestDto createDishRequestDto = new CreateDishRequestDto();
        createDishRequestDto.setAvailable(true);
        createDishRequestDto.setDescription("The characteristics of someone or something");
        createDishRequestDto.setName("Bella");
        createDishRequestDto.setPrice(new BigDecimal("2.3"));
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(createDishRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(
                                "/api/admin/{adminId}/restaurants/{restaurantId}/dishes", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":403,\"message\":\"Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten\"}"));
    }

    /**
     * Test {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     *   <li>Then status {@link StatusResultMatchers#isNotFound()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given UserRepository findById(Object) return empty; then status isNotFound()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto AdminDishController.addDish(Long, Long, CreateDishRequestDto)"
    })
    void testAddDish_givenUserRepositoryFindByIdReturnEmpty_thenStatusIsNotFound() throws Exception {
        // Arrange
        Optional<User> emptyResult = Optional.empty();

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("Hausnummer");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("Ort");
        owner.setPasswort("Passwort");
        owner.setPlz("Plz");
        owner.setRole(Role.USER);
        owner.setStrasse("Strasse");
        owner.setTelefon("Telefon");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(restaurant);
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("Hausnummer");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("Ort");
        owner2.setPasswort("Passwort");
        owner2.setPlz("Plz");
        owner2.setRole(Role.USER);
        owner2.setStrasse("Strasse");
        owner2.setTelefon("Telefon");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);
        Optional<Restaurant> ofResult = Optional.of(restaurant2);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant3);
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.USER);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        CreateDishRequestDto createDishRequestDto = new CreateDishRequestDto();
        createDishRequestDto.setAvailable(true);
        createDishRequestDto.setDescription("The characteristics of someone or something");
        createDishRequestDto.setName("Bella");
        createDishRequestDto.setPrice(new BigDecimal("2.3"));
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(createDishRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(
                                "/api/admin/{adminId}/restaurants/{restaurantId}/dishes", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":404,\"message\":\"Benutzer mit der ID 1 wurde nicht gefunden\"}"));
    }

    /**
     * Test {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Role is {@code ADMIN}.
     *   <li>Then status four hundred nine.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given User() Role is 'ADMIN'; then status four hundred nine")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto AdminDishController.addDish(Long, Long, CreateDishRequestDto)"
    })
    void testAddDish_givenUserRoleIsAdmin_thenStatusFourHundredNine() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.ADMIN);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("Hausnummer");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("Ort");
        owner.setPasswort("Passwort");
        owner.setPlz("Plz");
        owner.setRole(Role.USER);
        owner.setStrasse("Strasse");
        owner.setTelefon("Telefon");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(restaurant);
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("Hausnummer");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("Ort");
        owner2.setPasswort("Passwort");
        owner2.setPlz("Plz");
        owner2.setRole(Role.USER);
        owner2.setStrasse("Strasse");
        owner2.setTelefon("Telefon");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);
        Optional<Restaurant> ofResult2 = Optional.of(restaurant2);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant3);
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.USER);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        CreateDishRequestDto createDishRequestDto = new CreateDishRequestDto();
        createDishRequestDto.setAvailable(true);
        createDishRequestDto.setDescription("The characteristics of someone or something");
        createDishRequestDto.setName("Bella");
        createDishRequestDto.setPrice(new BigDecimal("2.3"));
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(createDishRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(
                                "/api/admin/{adminId}/restaurants/{restaurantId}/dishes", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(true);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().is(409))
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"status\":409,\"message\":\"Ein Gericht mit dem Namen \\\"Bella\\\" gibt es in diesem Restaurant bereits\"}"));
    }

    /**
     * Test {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Then status {@link StatusResultMatchers#isCreated()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishController#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto); then status isCreated()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto AdminDishController.addDish(Long, Long, CreateDishRequestDto)"
    })
    void testAddDish_thenStatusIsCreated() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.ADMIN);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("Hausnummer");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("Ort");
        owner.setPasswort("Passwort");
        owner.setPlz("Plz");
        owner.setRole(Role.USER);
        owner.setStrasse("Strasse");
        owner.setTelefon("Telefon");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(restaurant);
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("Hausnummer");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("Ort");
        owner2.setPasswort("Passwort");
        owner2.setPlz("Plz");
        owner2.setRole(Role.USER);
        owner2.setStrasse("Strasse");
        owner2.setTelefon("Telefon");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);
        Optional<Restaurant> ofResult2 = Optional.of(restaurant2);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant3);
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.USER);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        CreateDishRequestDto createDishRequestDto = new CreateDishRequestDto();
        createDishRequestDto.setAvailable(true);
        createDishRequestDto.setDescription("The characteristics of someone or something");
        createDishRequestDto.setName("Bella");
        createDishRequestDto.setPrice(new BigDecimal("2.3"));
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(createDishRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(
                                "/api/admin/{adminId}/restaurants/{restaurantId}/dishes", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(false);
        when(dishRepository.save(Mockito.<Dish>any())).thenReturn(dish);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminDishController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"id\":1,\"name\":\"Bella\",\"description\":\"The characteristics of someone or something\",\"price\":2.3,\"available"
                                                + "\":true}"));
    }
}