package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.CreateDishRequestDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapperImpl;
import at.htlkaindorf.backend_redogo.repository.CartItemRepository;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
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

@ContextConfiguration(
        classes = {
                AdminDishService.class,
                UserRepository.class,
                RestaurantRepository.class,
                DishRepository.class,
                CartItemRepository.class,
                MenuRepository.class,
                RestaurantMapper.class
        })
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AdminDishServiceDiffblueTest {
    @Autowired
    private AdminDishService adminDishService;

    @MockitoBean
    private CartItemRepository cartItemRepository;

    @MockitoBean
    private DishRepository dishRepository;

    @MockitoBean
    private MenuRepository menuRepository;

    @MockitoBean
    private RestaurantMapper restaurantMapper;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @MockitoBean
    private UserRepository userRepository;

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName("Test getOwnRestaurants(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findByOwnerIdOrderByNameAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findByOwnerIdOrderByNameAsc(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName("Test getOwnRestaurants(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants2() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName("Test getOwnRestaurants(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findByOwnerIdOrderByNameAsc(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));
        verify(restaurantRepository).findByOwnerIdOrderByNameAsc(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantMapper} {@link RestaurantMapper#toDto(List)} return {@link
     *       ArrayList#ArrayList()}.
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName(
            "Test getOwnRestaurants(Long); given RestaurantMapper toDto(List) return ArrayList(); then return Empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_givenRestaurantMapperToDtoReturnArrayList_thenReturnEmpty() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findByOwnerIdOrderByNameAsc(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any())).thenReturn(new ArrayList<>());

        // Act
        List<RestaurantDto> actualOwnRestaurants = adminDishService.getOwnRestaurants(1L);

        // Assert
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findByOwnerIdOrderByNameAsc(1L);
        verify(userRepository).findById(1L);
        assertTrue(actualOwnRestaurants.isEmpty());
    }

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName("Test getOwnRestaurants(Long); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#getOwnRestaurants(Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Role is {@code USER}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#getOwnRestaurants(Long)}
     */
    @Test
    @DisplayName(
            "Test getOwnRestaurants(Long); given User() Role is 'USER'; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List AdminDishService.getOwnRestaurants(Long)"})
    void testGetOwnRestaurants_givenUserRoleIsUser_thenThrowResponseStatusException() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 1L, new CreateDishRequestDto()));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish2() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addDish(1L, 1L, request));
        verify(dishRepository).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 1L, new CreateDishRequestDto()));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link DishRepository} {@link
     *       DishRepository#existsByRestaurantIdAndNameIgnoreCase(Long, String)} return {@code true}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given DishRepository existsByRestaurantIdAndNameIgnoreCase(Long, String) return 'true'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_givenDishRepositoryExistsByRestaurantIdAndNameIgnoreCaseReturnTrue() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(true);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addDish(1L, 1L, request));
        verify(dishRepository).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository} {@link RestaurantRepository#findById(Object)} return
     *       empty.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given RestaurantRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_givenRestaurantRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        Optional<Restaurant> emptyResult = Optional.empty();
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addDish(1L, 1L, request));
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>When {@link CreateDishRequestDto#CreateDishRequestDto()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given RestaurantRepository; when CreateDishRequestDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_givenRestaurantRepository_whenCreateDishRequestDto() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 1L, new CreateDishRequestDto()));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Id is two.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given User() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_givenUserIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addDish(1L, 1L, request));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addDish(Long, Long, CreateDishRequestDto); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addDish(1L, 1L, request));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Then return Name is {@code Bella}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addDish(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addDish(Long, Long, CreateDishRequestDto); then return Name is 'Bella'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addDish(Long, Long, CreateDishRequestDto)"})
    void testAddDish_thenReturnNameIsBella() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        DishRepository dishRepo = mock(DishRepository.class);
        when(dishRepo.existsByRestaurantIdAndNameIgnoreCase(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(false);
        when(dishRepo.save(Mockito.<Dish>any())).thenReturn(dish);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act
        MenuCardEntryDto actualAddDishResult = adminDishService.addDish(1L, 1L, request);

        // Assert
        verify(dishRepo).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
        verify(dishRepo).save(isA(Dish.class));
        assertEquals("Bella", actualAddDishResult.getName());
        assertEquals(
                "The characteristics of someone or something", actualAddDishResult.getDescription());
        assertEquals(1L, actualAddDishResult.getId().longValue());
        assertTrue(actualAddDishResult.isAvailable());
        assertEquals(new BigDecimal("2.3"), actualAddDishResult.getPrice());
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteDish(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        Optional<Dish> ofResult3 = Optional.of(dish);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(dishRepository)
                .deleteById(Mockito.<Long>any());
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        doNothing().when(cartItemRepository).deleteByDishId(Mockito.<Long>any());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(cartItemRepository).deleteByDishId(1L);
        verify(dishRepository).deleteById(1L);
        verify(dishRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteDish(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish2() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteDish(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(dishRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteDish(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish4() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(cartItemRepository)
                .deleteByDishId(Mockito.<Long>any());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(cartItemRepository).deleteByDishId(1L);
        verify(dishRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteDish(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish5() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link DishRepository} {@link DishRepository#findById(Object)} return empty.
     *   <li>Then calls {@link DishRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given DishRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenDishRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        DishRepository dishRepo = mock(DishRepository.class);
        Optional<Dish> emptyResult = Optional.empty();
        when(dishRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(dishRepo).findById(1L);
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link DishRepository} {@link DishRepository#flush()} does nothing.
     *   <li>Then calls {@link DishRepository#flush()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given DishRepository flush() does nothing; then calls flush()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenDishRepositoryFlushDoesNothing_thenCallsFlush() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        Optional<Dish> ofResult3 = Optional.of(dish);
        doNothing().when(dishRepository).flush();
        doNothing().when(dishRepository).deleteById(Mockito.<Long>any());
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        doNothing().when(cartItemRepository).deleteByDishId(Mockito.<Long>any());

        // Act
        adminDishService.deleteDish(1L, 1L, 1L);

        // Assert
        verify(cartItemRepository).deleteByDishId(1L);
        verify(dishRepository).flush();
        verify(dishRepository).deleteById(1L);
        verify(dishRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link Restaurant#Restaurant()} Id is two.
     *   <li>Then calls {@link DishRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given Restaurant() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenRestaurantIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
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
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(dishRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository} {@link RestaurantRepository#findById(Object)} return
     *       empty.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given RestaurantRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenRestaurantRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        Optional<Restaurant> emptyResult = Optional.empty();
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given RestaurantRepository; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenRestaurantRepository_thenThrowResponseStatusException() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Id is two.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given User() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenUserIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteDish(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteDish(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteDish(Long, Long, Long); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteDish(Long, Long, Long)"})
    void testDeleteDish_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        UserRepository userRepo = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteDish(1L, 1L, 1L));
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addMenu(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(menuRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addMenu(1L, 1L, request));
        verify(menuRepository).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addMenu(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu2() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addMenu(1L, 1L, new CreateDishRequestDto()));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addMenu(Long, Long, CreateDishRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addMenu(1L, 1L, new CreateDishRequestDto()));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link MenuRepository} {@link
     *       MenuRepository#existsByRestaurantIdAndNameIgnoreCase(Long, String)} return {@code true}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addMenu(Long, Long, CreateDishRequestDto); given MenuRepository existsByRestaurantIdAndNameIgnoreCase(Long, String) return 'true'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_givenMenuRepositoryExistsByRestaurantIdAndNameIgnoreCaseReturnTrue() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(menuRepository.existsByRestaurantIdAndNameIgnoreCase(
                Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(true);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addMenu(1L, 1L, request));
        verify(menuRepository).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository} {@link RestaurantRepository#findById(Object)} return
     *       empty.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addMenu(Long, Long, CreateDishRequestDto); given RestaurantRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_givenRestaurantRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        Optional<Restaurant> emptyResult = Optional.empty();
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addMenu(1L, 1L, request));
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>When {@link CreateDishRequestDto#CreateDishRequestDto()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addMenu(Long, Long, CreateDishRequestDto); given RestaurantRepository; when CreateDishRequestDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_givenRestaurantRepository_whenCreateDishRequestDto() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addMenu(1L, 1L, new CreateDishRequestDto()));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Id is two.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addMenu(Long, Long, CreateDishRequestDto); given User() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_givenUserIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addMenu(1L, 1L, request));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addMenu(Long, Long, CreateDishRequestDto); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.addMenu(1L, 1L, request));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}.
     *
     * <ul>
     *   <li>Then return Name is {@code Bella}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#addMenu(Long, Long, CreateDishRequestDto)}
     */
    @Test
    @DisplayName("Test addMenu(Long, Long, CreateDishRequestDto); then return Name is 'Bella'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"MenuCardEntryDto AdminDishService.addMenu(Long, Long, CreateDishRequestDto)"})
    void testAddMenu_thenReturnNameIsBella() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant4);

        MenuRepository menuRepo = mock(MenuRepository.class);
        when(menuRepo.existsByRestaurantIdAndNameIgnoreCase(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(false);
        when(menuRepo.save(Mockito.<Menu>any())).thenReturn(menu);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        CreateDishRequestDto request = new CreateDishRequestDto();
        request.setName("Bella");

        // Act
        MenuCardEntryDto actualAddMenuResult = adminDishService.addMenu(1L, 1L, request);

        // Assert
        verify(menuRepo).existsByRestaurantIdAndNameIgnoreCase(1L, "Bella");
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
        verify(menuRepo).save(isA(Menu.class));
        assertEquals("Bella", actualAddMenuResult.getName());
        assertEquals(
                "The characteristics of someone or something", actualAddMenuResult.getDescription());
        assertEquals(1L, actualAddMenuResult.getId().longValue());
        assertTrue(actualAddMenuResult.isAvailable());
        assertEquals(new BigDecimal("2.3"), actualAddMenuResult.getPrice());
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteMenu(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(menuRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(menuRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteMenu(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu2() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        doNothing().when(cartItemRepository).deleteByMenuId(Mockito.<Long>any());

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
        Optional<Menu> ofResult3 = Optional.of(menu);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(menuRepository)
                .deleteById(Mockito.<Long>any());
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(cartItemRepository).deleteByMenuId(1L);
        verify(menuRepository).deleteById(1L);
        verify(menuRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteMenu(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(cartItemRepository)
                .deleteByMenuId(Mockito.<Long>any());

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
        Optional<Menu> ofResult3 = Optional.of(menu);
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(cartItemRepository).deleteByMenuId(1L);
        verify(menuRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteMenu(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu4() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName("Test deleteMenu(Long, Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu5() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(restaurantRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link MenuRepository} {@link MenuRepository#findById(Object)} return empty.
     *   <li>Then calls {@link MenuRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given MenuRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenMenuRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        MenuRepository menuRepo = mock(MenuRepository.class);
        Optional<Menu> emptyResult = Optional.empty();
        when(menuRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(menuRepo).findById(1L);
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link MenuRepository} {@link MenuRepository#flush()} does nothing.
     *   <li>Then calls {@link MenuRepository#flush()}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given MenuRepository flush() does nothing; then calls flush()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenMenuRepositoryFlushDoesNothing_thenCallsFlush() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        doNothing().when(cartItemRepository).deleteByMenuId(Mockito.<Long>any());

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
        Optional<Menu> ofResult3 = Optional.of(menu);
        doNothing().when(menuRepository).flush();
        doNothing().when(menuRepository).deleteById(Mockito.<Long>any());
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        // Act
        adminDishService.deleteMenu(1L, 1L, 1L);

        // Assert
        verify(cartItemRepository).deleteByMenuId(1L);
        verify(menuRepository).flush();
        verify(menuRepository).deleteById(1L);
        verify(menuRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link Restaurant#Restaurant()} Id is two.
     *   <li>Then calls {@link MenuRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given Restaurant() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenRestaurantIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
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
        Optional<Menu> ofResult3 = Optional.of(menu);
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(menuRepository).findById(1L);
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository} {@link RestaurantRepository#findById(Object)} return
     *       empty.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given RestaurantRepository findById(Object) return empty; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenRestaurantRepositoryFindByIdReturnEmpty_thenCallsFindById() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RestaurantRepository restaurantRepo = mock(RestaurantRepository.class);
        Optional<Restaurant> emptyResult = Optional.empty();
        when(restaurantRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        DishRepository dishRepo = mock(DishRepository.class);
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        AdminDishService adminDishService =
                new AdminDishService(
                        userRepo, restaurantRepo, dishRepo, cartItemRepo, menuRepo, new RestaurantMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(restaurantRepo).findById(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given RestaurantRepository; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenRestaurantRepository_thenThrowResponseStatusException() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Id is two.
     *   <li>Then calls {@link RestaurantRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given User() Id is two; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenUserIdIsTwo_thenCallsFindById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(restaurantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link AdminDishService#deleteMenu(Long, Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link AdminDishService#deleteMenu(Long, Long, Long)}
     */
    @Test
    @DisplayName(
            "Test deleteMenu(Long, Long, Long); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"void AdminDishService.deleteMenu(Long, Long, Long)"})
    void testDeleteMenu_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> adminDishService.deleteMenu(1L, 1L, 1L));
        verify(userRepository).findById(1L);
    }
}
