package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.MenuCardDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {MenuCardService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class MenuCardServiceDiffblueTest {
    @MockitoBean
    private DishRepository dishRepository;

    @Autowired
    private MenuCardService menuCardService;

    @MockitoBean
    private MenuRepository menuRepository;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard() {
        // Arrange
        when(restaurantRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));
        verify(restaurantRepository).findById(1L);
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard2() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard3() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Given {@link Dish#Dish()} Available is {@code false}.
     *   <li>Then return Gerichte size is two.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName(
            "Test getMenuCard(Long); given Dish() Available is 'false'; then return Gerichte size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_givenDishAvailableIsFalse_thenReturnGerichteSizeIsTwo() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(new Location());
        restaurant3.setName("Bella");
        restaurant3.setOwner(new User());

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(restaurant3);
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Getting Speisekarte of Restaurant {}");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Getting Speisekarte of Restaurant {}");
        owner3.setPasswort("Getting Speisekarte of Restaurant {}");
        owner3.setPlz("Getting Speisekarte of Restaurant {}");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Getting Speisekarte of Restaurant {}");
        owner3.setTelefon("Getting Speisekarte of Restaurant {}");
        owner3.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location3);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner3);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(2L);
        restaurant5.setLocation(new Location());
        restaurant5.setName("Getting Speisekarte of Restaurant {}");
        restaurant5.setOwner(new User());

        Location location4 = new Location();
        location4.setDescription("Getting Speisekarte of Restaurant {}");
        location4.setId(2L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant5);
        location4.setStreetName("Getting Speisekarte of Restaurant {}");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("Getting Speisekarte of Restaurant {}");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Getting Speisekarte of Restaurant {}");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(2L);
        restaurant6.setLocation(location4);
        restaurant6.setName("Getting Speisekarte of Restaurant {}");
        restaurant6.setOwner(owner4);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("Getting Speisekarte of Restaurant {}");
        dish2.setId(2L);
        dish2.setName("Getting Speisekarte of Restaurant {}");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant6);

        ArrayList<Dish> dishList = new ArrayList<>();
        dishList.add(dish2);
        dishList.add(dish);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any())).thenReturn(dishList);
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        MenuCardDto actualMenuCard = menuCardService.getMenuCard(1L);

        // Assert
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
        List<MenuCardEntryDto> gerichte = actualMenuCard.getGerichte();
        assertEquals(2, gerichte.size());
        MenuCardEntryDto getResult = gerichte.get(1);
        assertEquals("Bella", getResult.getName());
        MenuCardEntryDto getResult2 = gerichte.get(0);
        assertEquals("Getting Speisekarte of Restaurant {}", getResult2.getDescription());
        assertEquals("Getting Speisekarte of Restaurant {}", getResult2.getName());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertFalse(getResult2.isAvailable());
        assertTrue(getResult.isAvailable());
        assertEquals(new BigDecimal("2.3"), getResult.getPrice());
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Given {@link Dish#Dish()} Available is {@code true}.
     *   <li>Then return Gerichte size is one.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName(
            "Test getMenuCard(Long); given Dish() Available is 'true'; then return Gerichte size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_givenDishAvailableIsTrue_thenReturnGerichteSizeIsOne() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(new Location());
        restaurant3.setName("Bella");
        restaurant3.setOwner(new User());

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(restaurant3);
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Getting Speisekarte of Restaurant {}");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Getting Speisekarte of Restaurant {}");
        owner3.setPasswort("Getting Speisekarte of Restaurant {}");
        owner3.setPlz("Getting Speisekarte of Restaurant {}");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Getting Speisekarte of Restaurant {}");
        owner3.setTelefon("Getting Speisekarte of Restaurant {}");
        owner3.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location3);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner3);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant4);

        ArrayList<Dish> dishList = new ArrayList<>();
        dishList.add(dish);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any())).thenReturn(dishList);
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        MenuCardDto actualMenuCard = menuCardService.getMenuCard(1L);

        // Assert
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
        List<MenuCardEntryDto> gerichte = actualMenuCard.getGerichte();
        assertEquals(1, gerichte.size());
        MenuCardEntryDto getResult = gerichte.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        assertEquals(1L, getResult.getId().longValue());
        assertTrue(getResult.isAvailable());
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Given {@link Menu#Menu()} Available is {@code false}.
     *   <li>Then return Menues size is two.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName(
            "Test getMenuCard(Long); given Menu() Available is 'false'; then return Menues size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_givenMenuAvailableIsFalse_thenReturnMenuesSizeIsTwo() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(new Location());
        restaurant3.setName("Bella");
        restaurant3.setOwner(new User());

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(restaurant3);
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Getting Speisekarte of Restaurant {}");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Getting Speisekarte of Restaurant {}");
        owner3.setPasswort("Getting Speisekarte of Restaurant {}");
        owner3.setPlz("Getting Speisekarte of Restaurant {}");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Getting Speisekarte of Restaurant {}");
        owner3.setTelefon("Getting Speisekarte of Restaurant {}");
        owner3.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location3);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner3);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant4);

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(2L);
        restaurant5.setLocation(new Location());
        restaurant5.setName("Getting Speisekarte of Restaurant {}");
        restaurant5.setOwner(new User());

        Location location4 = new Location();
        location4.setDescription("Getting Speisekarte of Restaurant {}");
        location4.setId(2L);
        location4.setPostalCode(1L);
        location4.setRestaurant(restaurant5);
        location4.setStreetName("Getting Speisekarte of Restaurant {}");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("Getting Speisekarte of Restaurant {}");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("Getting Speisekarte of Restaurant {}");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(2L);
        restaurant6.setLocation(location4);
        restaurant6.setName("Getting Speisekarte of Restaurant {}");
        restaurant6.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("Getting Speisekarte of Restaurant {}");
        menu2.setId(2L);
        menu2.setName("Getting Speisekarte of Restaurant {}");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant6);

        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.add(menu2);
        menuList.add(menu);
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any())).thenReturn(menuList);

        // Act
        MenuCardDto actualMenuCard = menuCardService.getMenuCard(1L);

        // Assert
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
        List<MenuCardEntryDto> menues = actualMenuCard.getMenues();
        assertEquals(2, menues.size());
        MenuCardEntryDto getResult = menues.get(1);
        assertEquals("Bella", getResult.getName());
        MenuCardEntryDto getResult2 = menues.get(0);
        assertEquals("Getting Speisekarte of Restaurant {}", getResult2.getDescription());
        assertEquals("Getting Speisekarte of Restaurant {}", getResult2.getName());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertFalse(getResult2.isAvailable());
        assertTrue(getResult.isAvailable());
        assertEquals(new BigDecimal("2.3"), getResult.getPrice());
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Given {@link Menu#Menu()} Available is {@code true}.
     *   <li>Then return Menues size is one.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName(
            "Test getMenuCard(Long); given Menu() Available is 'true'; then return Menues size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_givenMenuAvailableIsTrue_thenReturnMenuesSizeIsOne() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(new Location());
        restaurant3.setName("Bella");
        restaurant3.setOwner(new User());

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(restaurant3);
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("Getting Speisekarte of Restaurant {}");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("Getting Speisekarte of Restaurant {}");
        owner3.setPasswort("Getting Speisekarte of Restaurant {}");
        owner3.setPlz("Getting Speisekarte of Restaurant {}");
        owner3.setRole(Role.USER);
        owner3.setStrasse("Getting Speisekarte of Restaurant {}");
        owner3.setTelefon("Getting Speisekarte of Restaurant {}");
        owner3.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location3);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner3);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant4);

        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any())).thenReturn(menuList);

        // Act
        MenuCardDto actualMenuCard = menuCardService.getMenuCard(1L);

        // Assert
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
        List<MenuCardEntryDto> menues = actualMenuCard.getMenues();
        assertEquals(1, menues.size());
        MenuCardEntryDto getResult = menues.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        assertEquals(1L, getResult.getId().longValue());
        assertTrue(getResult.isAvailable());
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository} {@link RestaurantRepository#findById(Object)} return
     *       empty.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long); given RestaurantRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_givenRestaurantRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Restaurant> emptyResult = Optional.empty();
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));
        verify(restaurantRepository).findById(1L);
    }

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Then return RestaurantName is {@code Bella}.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long); then return RestaurantName is 'Bella'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardDto MenuCardService.getMenuCard(Long)"})
    void testGetMenuCard_thenReturnRestaurantNameIsBella() {
        // Arrange
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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dishRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(menuRepository.findByRestaurantIdOrderByIdAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        MenuCardDto actualMenuCard = menuCardService.getMenuCard(1L);

        // Assert
        verify(dishRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(menuRepository).findByRestaurantIdOrderByIdAsc(1L);
        verify(restaurantRepository).findById(1L);
        assertEquals("Bella", actualMenuCard.getRestaurantName());
        assertEquals(1L, actualMenuCard.getRestaurantId().longValue());
        List<MenuCardEntryDto> gerichte = actualMenuCard.getGerichte();
        assertTrue(gerichte.isEmpty());
        assertSame(gerichte, actualMenuCard.getMenues());
    }
}
