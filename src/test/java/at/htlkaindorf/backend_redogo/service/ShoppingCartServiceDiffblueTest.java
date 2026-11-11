package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.AddToCartRequestDto;
import at.htlkaindorf.backend_redogo.dto.CartDto;
import at.htlkaindorf.backend_redogo.dto.CartItemDto;
import at.htlkaindorf.backend_redogo.dto.CartItemType;
import at.htlkaindorf.backend_redogo.entity.CartItem;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.ShoppingCart;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.ShoppingCartMapper;
import at.htlkaindorf.backend_redogo.mapper.ShoppingCartMapperImpl;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.ShoppingCartRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
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

@ContextConfiguration(classes = {ShoppingCartService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ShoppingCartServiceDiffblueTest {
    @MockitoBean
    private DishRepository dishRepository;

    @MockitoBean
    private MenuRepository menuRepository;

    @MockitoBean
    private ShoppingCartMapper shoppingCartMapper;

    @MockitoBean
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @MockitoBean
    private UserRepository userRepository;

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart() {
        // Arrange
        Optional<ShoppingCart> emptyResult = Optional.empty();
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(emptyResult);

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
        when(shoppingCartMapper.emptyCart(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(shoppingCartMapper).emptyCart(1L);
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart2() {
        // Arrange
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

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
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart3() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart4() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper} {@link ShoppingCartMapper#emptyCart(Long)} return {@link
     *       CartDto#CartDto()}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName(
            "Test getCart(Long); given ShoppingCartMapper emptyCart(Long) return CartDto(); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_givenShoppingCartMapperEmptyCartReturnCartDto_thenReturnCartDto() {
        // Arrange
        Optional<ShoppingCart> emptyResult = Optional.empty();
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(emptyResult);

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
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.emptyCart(Mockito.<Long>any())).thenReturn(cartDto);

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(shoppingCartMapper).emptyCart(1L);
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualCart);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper} {@link ShoppingCartMapper#toDto(ShoppingCart)} return
     *       {@link CartDto#CartDto()}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName(
            "Test getCart(Long); given ShoppingCartMapper toDto(ShoppingCart) return CartDto(); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_givenShoppingCartMapperToDtoReturnCartDto_thenReturnCartDto() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualCart);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return Items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName(
            "Test getCart(Long); given User() Email is 'john.smith@example.org'; then return Items size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_givenUserEmailIsJohnSmithExampleOrg_thenReturnItemsSizeIsTwo() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(2L);
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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(2L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(2);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        user2.setId(2L);
        user2.setNachname("Nachname");
        user2.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        user2.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        user2.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        user2.setRole(Role.ADMIN);
        user2.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        user2.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        user2.setVorname("Vorname");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);

        Location location3 = new Location();
        location3.setDescription("Description");
        location3.setId(2L);
        location3.setPostalCode(2L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Street Name");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setId(2L);
        owner3.setNachname("Nachname");
        owner3.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setRole(Role.ADMIN);
        owner3.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        owner3.setVorname("Vorname");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(2L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Name");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("Description");
        dish2.setId(2L);
        dish2.setName("Name");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("Description");
        location4.setId(2L);
        location4.setPostalCode(2L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("Street Name");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setId(2L);
        owner4.setNachname("Nachname");
        owner4.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        owner4.setVorname("Vorname");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Name");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("Description");
        menu2.setId(2L);
        menu2.setName("Name");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(2L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(2);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user3);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> items2 = actualCart.getItems();
        assertEquals(2, items2.size());
        CartItemDto getResult = items2.get(1);
        assertEquals("Bella", getResult.getName());
        CartItemDto getResult2 = items2.get(0);
        assertEquals("Name", getResult2.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(2, getResult.getQuantity());
        assertEquals(2L, getResult2.getId().longValue());
        assertEquals(2L, getResult2.getItemId().longValue());
        assertEquals(CartItemType.DISH, getResult.getType());
        assertEquals(new BigDecimal("2.3"), getResult.getUnitPrice());
        assertEquals(new BigDecimal("4.6"), getResult.getLineTotal());
        assertEquals(new BigDecimal("9.20"), actualCart.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Then return Id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long); then return Id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_thenReturnIdLongValueIsOne() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        assertEquals(1L, actualCart.getId().longValue());
        assertEquals(1L, actualCart.getUserId().longValue());
        assertTrue(actualCart.getItems().isEmpty());
        assertEquals(new BigDecimal("0.00"), actualCart.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Then return Items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long); then return Items size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart_thenReturnItemsSizeIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(2L);
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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(2L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(2);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> items2 = actualCart.getItems();
        assertEquals(1, items2.size());
        CartItemDto getResult = items2.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(new BigDecimal("4.60"), actualCart.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem2() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(menuRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(menuRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem3() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem4() {
        // Arrange
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

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

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem5() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(dishRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem6() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link AddToCartRequestDto} (default constructor) Type is {@code DISH}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given AddToCartRequestDto (default constructor) Type is 'DISH'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenAddToCartRequestDtoTypeIsDish_thenReturnCartDto() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);

        DishRepository dishRepo = mock(DishRepository.class);
        when(dishRepo.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(new Location());
        restaurant3.setName("Bella");
        restaurant3.setOwner(new User());

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(restaurant3);
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

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location2);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant4);
        Optional<Menu> ofResult4 = Optional.of(menu);

        MenuRepository menuRepo = mock(MenuRepository.class);
        when(menuRepo.findById(Mockito.<Long>any())).thenReturn(ofResult4);

        ShoppingCartMapper mapper = mock(ShoppingCartMapper.class);
        CartDto cartDto = new CartDto();
        when(mapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(2L);
        request.setQuantity(99);
        request.setType(CartItemType.DISH);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(cartRepo, userRepo, dishRepo, menuRepo, mapper);
        shoppingCartService.addItem(2L, request);

        AddToCartRequestDto request2 = new AddToCartRequestDto();
        request2.setItemId(1L);
        request2.setQuantity(1);
        request2.setType(CartItemType.MENU);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request2);

        // Assert
        verify(mapper, atLeast(1)).toDto(isA(ShoppingCart.class));
        verify(cartRepo, atLeast(1)).findByUserId(Mockito.<Long>any());
        verify(cartRepo, atLeast(1)).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo, atLeast(1)).findById(Mockito.<Long>any());
        verify(menuRepo).findById(1L);
        verify(dishRepo).findById(2L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link AddToCartRequestDto} (default constructor) Type is {@code MENU}.
     *   <li>Then calls {@link MenuRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given AddToCartRequestDto (default constructor) Type is 'MENU'; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenAddToCartRequestDtoTypeIsMenu_thenCallsFindById() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);
        Optional<Menu> ofResult3 = Optional.of(menu);

        MenuRepository menuRepo = mock(MenuRepository.class);
        when(menuRepo.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(2L);
        request.setQuantity(99);
        request.setType(CartItemType.MENU);
        DishRepository dishRepo = mock(DishRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());
        shoppingCartService.addItem(2L, request);

        AddToCartRequestDto request2 = new AddToCartRequestDto();
        request2.setItemId(1L);
        request2.setQuantity(1);
        request2.setType(CartItemType.MENU);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request2));
        verify(cartRepo, atLeast(1)).findByUserId(Mockito.<Long>any());
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(menuRepo, atLeast(1)).findById(Mockito.<Long>any());
        verify(userRepo, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link Dish#Dish()} Available is {@code false}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given Dish() Available is 'false'; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenDishAvailableIsFalse_thenThrowResponseStatusException() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(false);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link Menu#Menu()} Available is {@code false}.
     *   <li>Then calls {@link MenuRepository#findById(Object)}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given Menu() Available is 'false'; then calls findById(Object)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenMenuAvailableIsFalse_thenCallsFindById() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Menu menu = new Menu();
        menu.setAvailable(false);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);
        Optional<Menu> ofResult3 = Optional.of(menu);
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(menuRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link MenuRepository} {@link MenuRepository#findById(Object)} return of {@link
     *       Menu#Menu()}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given MenuRepository findById(Object) return of Menu(); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenMenuRepositoryFindByIdReturnOfMenu_thenReturnCartDto() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);
        Optional<Menu> ofResult3 = Optional.of(menu);
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(menuRepository).findById(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper} {@link ShoppingCartMapper#toDto(ShoppingCart)} return
     *       {@link CartDto#CartDto()}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given ShoppingCartMapper toDto(ShoppingCart) return CartDto(); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenShoppingCartMapperToDtoReturnCartDto_thenReturnCartDto() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartRepository} {@link ShoppingCartRepository#findByUserId(Long)}
     *       return empty.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given ShoppingCartRepository findByUserId(Long) return empty; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenShoppingCartRepositoryFindByUserIdReturnEmpty_thenReturnCartDto() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart);
        Optional<ShoppingCart> emptyResult = Optional.empty();
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(emptyResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user2);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);
        Optional<Menu> ofResult2 = Optional.of(menu);

        MenuRepository menuRepo = mock(MenuRepository.class);
        when(menuRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        ShoppingCartMapper mapper = mock(ShoppingCartMapper.class);
        CartDto cartDto = new CartDto();
        when(mapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(cartRepo, userRepo, mock(DishRepository.class), menuRepo, mapper);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(mapper).toDto(isA(ShoppingCart.class));
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(menuRepo).findById(1L);
        verify(userRepo).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'john.smith@example.org'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserEmailIsJohnSmithExampleOrg_thenReturnCartDto() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("User {}: {}x {} {} im Warenkorb");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("User {}: {}x {} {} im Warenkorb");
        user.setPasswort("User {}: {}x {} {} im Warenkorb");
        user.setPlz("User {}: {}x {} {} im Warenkorb");
        user.setRole(Role.USER);
        user.setStrasse("User {}: {}x {} {} im Warenkorb");
        user.setTelefon("User {}: {}x {} {} im Warenkorb");
        user.setVorname("Bella");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("User {}: {}x {} {} im Warenkorb");
        owner.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner.setPlz("User {}: {}x {} {} im Warenkorb");
        owner.setRole(Role.USER);
        owner.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("User {}: {}x {} {} im Warenkorb");
        owner2.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner2.setPlz("User {}: {}x {} {} im Warenkorb");
        owner2.setRole(Role.USER);
        owner2.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner2.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(2L);
        user2.setNachname("User {}: {}x {} {} im Warenkorb");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.ADMIN);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("User {}: {}x {} {} im Warenkorb");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);

        Location location3 = new Location();
        location3.setDescription("User {}: {}x {} {} im Warenkorb");
        location3.setId(2L);
        location3.setPostalCode(99L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("User {}: {}x {} {} im Warenkorb");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(2L);
        owner3.setNachname("User {}: {}x {} {} im Warenkorb");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.ADMIN);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("User {}: {}x {} {} im Warenkorb");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(2L);
        restaurant3.setLocation(location3);
        restaurant3.setName("User {}: {}x {} {} im Warenkorb");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("User {}: {}x {} {} im Warenkorb");
        dish2.setId(2L);
        dish2.setName("User {}: {}x {} {} im Warenkorb");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("User {}: {}x {} {} im Warenkorb");
        location4.setId(2L);
        location4.setPostalCode(99L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("User {}: {}x {} {} im Warenkorb");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("User {}: {}x {} {} im Warenkorb");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("User {}: {}x {} {} im Warenkorb");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
        restaurant4.setLocation(location4);
        restaurant4.setName("User {}: {}x {} {} im Warenkorb");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("User {}: {}x {} {} im Warenkorb");
        menu2.setId(2L);
        menu2.setName("User {}: {}x {} {} im Warenkorb");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(2L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user3);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user4);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(1L);
        restaurant5.setLocation(new Location());
        restaurant5.setName("Bella");
        restaurant5.setOwner(new User());

        Location location5 = new Location();
        location5.setDescription("The characteristics of someone or something");
        location5.setId(1L);
        location5.setPostalCode(1L);
        location5.setRestaurant(restaurant5);
        location5.setStreetName("Bella");

        User owner5 = new User();
        owner5.setEmail("jane.doe@example.org");
        owner5.setHausnummer("Hausnummer");
        owner5.setId(1L);
        owner5.setNachname("Bella");
        owner5.setOrt("Ort");
        owner5.setPasswort("Passwort");
        owner5.setPlz("Plz");
        owner5.setRole(Role.USER);
        owner5.setStrasse("Strasse");
        owner5.setTelefon("Telefon");
        owner5.setVorname("Bella");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(1L);
        restaurant6.setLocation(location5);
        restaurant6.setName("Bella");
        restaurant6.setOwner(owner5);

        Dish dish3 = new Dish();
        dish3.setAvailable(true);
        dish3.setDescription("The characteristics of someone or something");
        dish3.setId(1L);
        dish3.setName("Bella");
        dish3.setPrice(new BigDecimal("2.3"));
        dish3.setRestaurant(restaurant6);
        Optional<Dish> ofResult3 = Optional.of(dish3);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return Items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'john.smith@example.org'; then return Items size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserEmailIsJohnSmithExampleOrg_thenReturnItemsSizeIsTwo() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("User {}: {}x {} {} im Warenkorb");
        user2.setPasswort("User {}: {}x {} {} im Warenkorb");
        user2.setPlz("User {}: {}x {} {} im Warenkorb");
        user2.setRole(Role.USER);
        user2.setStrasse("User {}: {}x {} {} im Warenkorb");
        user2.setTelefon("User {}: {}x {} {} im Warenkorb");
        user2.setVorname("Bella");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user2);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("User {}: {}x {} {} im Warenkorb");
        owner.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner.setPlz("User {}: {}x {} {} im Warenkorb");
        owner.setRole(Role.USER);
        owner.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("User {}: {}x {} {} im Warenkorb");
        owner2.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner2.setPlz("User {}: {}x {} {} im Warenkorb");
        owner2.setRole(Role.USER);
        owner2.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner2.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        User user3 = new User();
        user3.setEmail("john.smith@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(2L);
        user3.setNachname("User {}: {}x {} {} im Warenkorb");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.ADMIN);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("User {}: {}x {} {} im Warenkorb");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user3);

        Location location3 = new Location();
        location3.setDescription("User {}: {}x {} {} im Warenkorb");
        location3.setId(2L);
        location3.setPostalCode(99L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("User {}: {}x {} {} im Warenkorb");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(2L);
        owner3.setNachname("User {}: {}x {} {} im Warenkorb");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.ADMIN);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("User {}: {}x {} {} im Warenkorb");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(2L);
        restaurant3.setLocation(location3);
        restaurant3.setName("User {}: {}x {} {} im Warenkorb");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("User {}: {}x {} {} im Warenkorb");
        dish2.setId(2L);
        dish2.setName("User {}: {}x {} {} im Warenkorb");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("User {}: {}x {} {} im Warenkorb");
        location4.setId(2L);
        location4.setPostalCode(99L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("User {}: {}x {} {} im Warenkorb");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("User {}: {}x {} {} im Warenkorb");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("User {}: {}x {} {} im Warenkorb");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
        restaurant4.setLocation(location4);
        restaurant4.setName("User {}: {}x {} {} im Warenkorb");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("User {}: {}x {} {} im Warenkorb");
        menu2.setId(2L);
        menu2.setName("User {}: {}x {} {} im Warenkorb");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(2L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items);
        shoppingCart2.setUser(user4);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(1L);
        restaurant5.setLocation(new Location());
        restaurant5.setName("Bella");
        restaurant5.setOwner(new User());

        Location location5 = new Location();
        location5.setDescription("The characteristics of someone or something");
        location5.setId(1L);
        location5.setPostalCode(1L);
        location5.setRestaurant(restaurant5);
        location5.setStreetName("Bella");

        User owner5 = new User();
        owner5.setEmail("jane.doe@example.org");
        owner5.setHausnummer("Hausnummer");
        owner5.setId(1L);
        owner5.setNachname("Bella");
        owner5.setOrt("Ort");
        owner5.setPasswort("Passwort");
        owner5.setPlz("Plz");
        owner5.setRole(Role.USER);
        owner5.setStrasse("Strasse");
        owner5.setTelefon("Telefon");
        owner5.setVorname("Bella");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(1L);
        restaurant6.setLocation(location5);
        restaurant6.setName("Bella");
        restaurant6.setOwner(owner5);

        Dish dish3 = new Dish();
        dish3.setAvailable(true);
        dish3.setDescription("The characteristics of someone or something");
        dish3.setId(1L);
        dish3.setName("Bella");
        dish3.setPrice(new BigDecimal("2.3"));
        dish3.setRestaurant(restaurant6);
        Optional<Dish> ofResult3 = Optional.of(dish3);

        DishRepository dishRepo = mock(DishRepository.class);
        when(dishRepo.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepo).findById(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> items2 = actualAddItemResult.getItems();
        assertEquals(2, items2.size());
        CartItemDto getResult = items2.get(1);
        assertEquals("Bella", getResult.getName());
        CartItemDto getResult2 = items2.get(0);
        assertEquals("User {}: {}x {} {} im Warenkorb", getResult2.getName());
        assertEquals(1, getResult.getQuantity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertEquals(2L, getResult2.getItemId().longValue());
        assertEquals(99, getResult2.getQuantity());
        assertEquals(CartItemType.DISH, getResult.getType());
        assertEquals(new BigDecimal("2.3"), getResult.getLineTotal());
        assertEquals(new BigDecimal("2.3"), getResult.getUnitPrice());
        assertEquals(new BigDecimal("227.7"), getResult2.getLineTotal());
        assertEquals(new BigDecimal("230.00"), actualAddItemResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code prof.einstein@example.org}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'prof.einstein@example.org'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserEmailIsProfEinsteinExampleOrg_thenReturnCartDto() {
        // Arrange
        User user = new User();
        user.setEmail("prof.einstein@example.org");
        user.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        user.setId(3L);
        user.setNachname("Nachname");
        user.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        user.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        user.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        user.setRole(Role.USER);
        user.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        user.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        user.setVorname("Vorname");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(3L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("Description");
        location.setId(3L);
        location.setPostalCode(2L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Street Name");

        User owner = new User();
        owner.setEmail("prof.einstein@example.org");
        owner.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        owner.setId(3L);
        owner.setNachname("Nachname");
        owner.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        owner.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        owner.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        owner.setRole(Role.USER);
        owner.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        owner.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        owner.setVorname("Vorname");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);
        restaurant.setLocation(location);
        restaurant.setName("Name");
        restaurant.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("Description");
        dish.setId(3L);
        dish.setName("Name");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("Description");
        location2.setId(3L);
        location2.setPostalCode(2L);
        location2.setRestaurant(new Restaurant());
        location2.setStreetName("Street Name");

        User owner2 = new User();
        owner2.setEmail("prof.einstein@example.org");
        owner2.setHausnummer("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setId(3L);
        owner2.setNachname("Nachname");
        owner2.setOrt("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setPlz("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setRole(Role.USER);
        owner2.setStrasse("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setTelefon("at.htlkaindorf.backend_redogo.entity.User");
        owner2.setVorname("Vorname");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(3L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Name");
        restaurant2.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("Description");
        menu.setId(3L);
        menu.setName("Name");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(3L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(2);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Menu menu2 = new Menu();
        menu2.setAvailable(true);
        menu2.setDescription("The characteristics of someone or something");
        menu2.setId(1L);
        menu2.setName("Bella");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);
        Optional<Menu> ofResult3 = Optional.of(menu2);
        when(menuRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(menuRepository).findById(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Hausnummer is {@code User {}: {}x {} {} im Warenkorb}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Hausnummer is 'User {}: {}x {} {} im Warenkorb'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserHausnummerIsUserXImWarenkorb_thenReturnCartDto() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("User {}: {}x {} {} im Warenkorb");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("User {}: {}x {} {} im Warenkorb");
        user.setPasswort("User {}: {}x {} {} im Warenkorb");
        user.setPlz("User {}: {}x {} {} im Warenkorb");
        user.setRole(Role.USER);
        user.setStrasse("User {}: {}x {} {} im Warenkorb");
        user.setTelefon("User {}: {}x {} {} im Warenkorb");
        user.setVorname("Bella");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("User {}: {}x {} {} im Warenkorb");
        owner.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner.setPlz("User {}: {}x {} {} im Warenkorb");
        owner.setRole(Role.USER);
        owner.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("User {}: {}x {} {} im Warenkorb");
        owner2.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner2.setPlz("User {}: {}x {} {} im Warenkorb");
        owner2.setRole(Role.USER);
        owner2.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner2.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant4);
        Optional<Dish> ofResult3 = Optional.of(dish2);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepository).findById(1L);
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualAddItemResult);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        UserRepository userRepo = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        mock(ShoppingCartRepository.class),
                        userRepo,
                        mock(DishRepository.class),
                        mock(MenuRepository.class),
                        mock(ShoppingCartMapper.class));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.MENU);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given zero.
     *   <li>When {@link AddToCartRequestDto} (default constructor) Quantity is zero.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given zero; when AddToCartRequestDto (default constructor) Quantity is zero")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenZero_whenAddToCartRequestDtoQuantityIsZero() {
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

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(0);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Then return Id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); then return Id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_thenReturnIdLongValueIsOne() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user2);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(new Location());
        restaurant.setName("Bella");
        restaurant.setOwner(new User());

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(restaurant);
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

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant2);
        Optional<Dish> ofResult3 = Optional.of(dish);

        DishRepository dishRepo = mock(DishRepository.class);
        when(dishRepo.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepo).findById(1L);
        verify(userRepo).findById(1L);
        assertEquals(1L, actualAddItemResult.getId().longValue());
        assertEquals(1L, actualAddItemResult.getUserId().longValue());
        assertTrue(actualAddItemResult.getItems().isEmpty());
        assertEquals(new BigDecimal("0.00"), actualAddItemResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Then return Items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); then return Items size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_thenReturnItemsSizeIsOne() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("User {}: {}x {} {} im Warenkorb");
        user2.setPasswort("User {}: {}x {} {} im Warenkorb");
        user2.setPlz("User {}: {}x {} {} im Warenkorb");
        user2.setRole(Role.USER);
        user2.setStrasse("User {}: {}x {} {} im Warenkorb");
        user2.setTelefon("User {}: {}x {} {} im Warenkorb");
        user2.setVorname("Bella");

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user2);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(1L);
        location.setRestaurant(new Restaurant());
        location.setStreetName("Bella");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner.setId(1L);
        owner.setNachname("Bella");
        owner.setOrt("User {}: {}x {} {} im Warenkorb");
        owner.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner.setPlz("User {}: {}x {} {} im Warenkorb");
        owner.setRole(Role.USER);
        owner.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner.setVorname("Bella");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setLocation(location);
        restaurant.setName("Bella");
        restaurant.setOwner(owner);

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
        location2.setStreetName("Bella");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setHausnummer("User {}: {}x {} {} im Warenkorb");
        owner2.setId(1L);
        owner2.setNachname("Bella");
        owner2.setOrt("User {}: {}x {} {} im Warenkorb");
        owner2.setPasswort("User {}: {}x {} {} im Warenkorb");
        owner2.setPlz("User {}: {}x {} {} im Warenkorb");
        owner2.setRole(Role.USER);
        owner2.setStrasse("User {}: {}x {} {} im Warenkorb");
        owner2.setTelefon("User {}: {}x {} {} im Warenkorb");
        owner2.setVorname("Bella");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(1L);
        restaurant2.setLocation(location2);
        restaurant2.setName("Bella");
        restaurant2.setOwner(owner2);

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items);
        shoppingCart2.setUser(user3);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant4);
        Optional<Dish> ofResult3 = Optional.of(dish2);

        DishRepository dishRepo = mock(DishRepository.class);
        when(dishRepo.findById(Mockito.<Long>any())).thenReturn(ofResult3);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        CartDto actualAddItemResult = shoppingCartService.addItem(1L, request);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(dishRepo).findById(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> items2 = actualAddItemResult.getItems();
        assertEquals(1, items2.size());
        CartItemDto getResult = items2.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals(1, getResult.getQuantity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(new BigDecimal("2.3"), getResult.getLineTotal());
        assertEquals(new BigDecimal("2.30"), actualAddItemResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem2() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem3() {
        // Arrange
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

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
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper} {@link ShoppingCartMapper#toDto(ShoppingCart)} return
     *       {@link CartDto#CartDto()}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given ShoppingCartMapper toDto(ShoppingCart) return CartDto(); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenShoppingCartMapperToDtoReturnCartDto_thenReturnCartDto() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualRemoveItemResult);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given ShoppingCartMapper; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenShoppingCartMapper_thenThrowResponseStatusException() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartRepository} {@link ShoppingCartRepository#findByUserId(Long)}
     *       return empty.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given ShoppingCartRepository findByUserId(Long) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenShoppingCartRepositoryFindByUserIdReturnEmpty() {
        // Arrange
        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        Optional<ShoppingCart> emptyResult = Optional.empty();
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(emptyResult);

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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo,
                        userRepo,
                        mock(DishRepository.class),
                        mock(MenuRepository.class),
                        mock(ShoppingCartMapper.class));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartRepository}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given ShoppingCartRepository; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenShoppingCartRepository_thenThrowResponseStatusException() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given User() Email is 'john.smith@example.org'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenUserEmailIsJohnSmithExampleOrg_thenReturnCartDto() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(2L);
        user2.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.ADMIN);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);

        Location location3 = new Location();
        location3.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        location3.setId(2L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("User {}: Position {} aus dem Warenkorb entfernt");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(2L);
        owner3.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.ADMIN);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(2L);
        restaurant3.setLocation(location3);
        restaurant3.setName("User {}: Position {} aus dem Warenkorb entfernt");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        dish2.setId(2L);
        dish2.setName("User {}: Position {} aus dem Warenkorb entfernt");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        location4.setId(2L);
        location4.setPostalCode(1L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("User {}: Position {} aus dem Warenkorb entfernt");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
        restaurant4.setLocation(location4);
        restaurant4.setName("User {}: Position {} aus dem Warenkorb entfernt");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        menu2.setId(2L);
        menu2.setName("User {}: Position {} aus dem Warenkorb entfernt");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(2L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user3);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user4);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualRemoveItemResult);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findById(Object)} return empty.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long); given UserRepository findById(Object) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenUserRepositoryFindByIdReturnEmpty() {
        // Arrange
        UserRepository userRepo = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        mock(ShoppingCartRepository.class),
                        userRepo,
                        mock(DishRepository.class),
                        mock(MenuRepository.class),
                        mock(ShoppingCartMapper.class));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Then return Id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long); then return Id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_thenReturnIdLongValueIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        assertEquals(1L, actualRemoveItemResult.getId().longValue());
        assertEquals(1L, actualRemoveItemResult.getUserId().longValue());
        assertTrue(actualRemoveItemResult.getItems().isEmpty());
        assertEquals(new BigDecimal("0.00"), actualRemoveItemResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Then return Items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long); then return Items size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_thenReturnItemsSizeIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setRole(Role.USER);
        user3.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setVorname("Bella");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(1L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user3);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(5L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setRole(Role.USER);
        owner3.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(5L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setRole(Role.USER);
        owner4.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(true);
        menu2.setDescription("The characteristics of someone or something");
        menu2.setId(1L);
        menu2.setName("Bella");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(1L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(5);

        ArrayList<CartItem> items2 = new ArrayList<>();
        items2.add(cartItem2);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items2);
        shoppingCart2.setUser(user4);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> items3 = actualRemoveItemResult.getItems();
        assertEquals(1, items3.size());
        CartItemDto getResult = items3.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(5, getResult.getQuantity());
        assertEquals(new BigDecimal("11.5"), getResult.getLineTotal());
        assertEquals(new BigDecimal("11.50"), actualRemoveItemResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Then return Items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long); then return Items size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_thenReturnItemsSizeIsTwo() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setRole(Role.USER);
        user3.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        user3.setVorname("Bella");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(1L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user3);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(5L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setRole(Role.USER);
        owner3.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(5L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setPasswort("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setPlz("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setRole(Role.USER);
        owner4.setStrasse("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setTelefon("User {}: Position {} aus dem Warenkorb entfernt");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(true);
        menu2.setDescription("The characteristics of someone or something");
        menu2.setId(1L);
        menu2.setName("Bella");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(1L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(5);

        User user4 = new User();
        user4.setEmail("john.smith@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(2L);
        user4.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.ADMIN);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        ShoppingCart cart3 = new ShoppingCart();
        cart3.setId(2L);
        cart3.setItems(new ArrayList<>());
        cart3.setUser(user4);

        Location location5 = new Location();
        location5.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        location5.setId(2L);
        location5.setPostalCode(1L);
        location5.setRestaurant(new Restaurant());
        location5.setStreetName("User {}: Position {} aus dem Warenkorb entfernt");

        User owner5 = new User();
        owner5.setEmail("john.smith@example.org");
        owner5.setHausnummer("Hausnummer");
        owner5.setId(2L);
        owner5.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        owner5.setOrt("Ort");
        owner5.setPasswort("Passwort");
        owner5.setPlz("Plz");
        owner5.setRole(Role.ADMIN);
        owner5.setStrasse("Strasse");
        owner5.setTelefon("Telefon");
        owner5.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(2L);
        restaurant5.setLocation(location5);
        restaurant5.setName("User {}: Position {} aus dem Warenkorb entfernt");
        restaurant5.setOwner(owner5);

        Dish dish3 = new Dish();
        dish3.setAvailable(false);
        dish3.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        dish3.setId(2L);
        dish3.setName("User {}: Position {} aus dem Warenkorb entfernt");
        dish3.setPrice(new BigDecimal("2.3"));
        dish3.setRestaurant(restaurant5);

        Location location6 = new Location();
        location6.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        location6.setId(2L);
        location6.setPostalCode(1L);
        location6.setRestaurant(new Restaurant());
        location6.setStreetName("User {}: Position {} aus dem Warenkorb entfernt");

        User owner6 = new User();
        owner6.setEmail("john.smith@example.org");
        owner6.setHausnummer("Hausnummer");
        owner6.setId(2L);
        owner6.setNachname("User {}: Position {} aus dem Warenkorb entfernt");
        owner6.setOrt("Ort");
        owner6.setPasswort("Passwort");
        owner6.setPlz("Plz");
        owner6.setRole(Role.ADMIN);
        owner6.setStrasse("Strasse");
        owner6.setTelefon("Telefon");
        owner6.setVorname("User {}: Position {} aus dem Warenkorb entfernt");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(2L);
        restaurant6.setLocation(location6);
        restaurant6.setName("User {}: Position {} aus dem Warenkorb entfernt");
        restaurant6.setOwner(owner6);

        Menu menu3 = new Menu();
        menu3.setAvailable(false);
        menu3.setDescription("User {}: Position {} aus dem Warenkorb entfernt");
        menu3.setId(2L);
        menu3.setName("User {}: Position {} aus dem Warenkorb entfernt");
        menu3.setPrice(new BigDecimal("2.3"));
        menu3.setRestaurant(restaurant6);

        CartItem cartItem3 = new CartItem();
        cartItem3.setCart(cart3);
        cartItem3.setDish(dish3);
        cartItem3.setId(2L);
        cartItem3.setMenu(menu3);
        cartItem3.setQuantity(1);

        ArrayList<CartItem> items2 = new ArrayList<>();
        items2.add(cartItem3);
        items2.add(cartItem2);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items2);
        shoppingCart2.setUser(user5);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setHausnummer("Hausnummer");
        user6.setId(1L);
        user6.setNachname("Bella");
        user6.setOrt("Ort");
        user6.setPasswort("Passwort");
        user6.setPlz("Plz");
        user6.setRole(Role.USER);
        user6.setStrasse("Strasse");
        user6.setTelefon("Telefon");
        user6.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user6);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> items3 = actualRemoveItemResult.getItems();
        assertEquals(2, items3.size());
        CartItemDto getResult = items3.get(1);
        assertEquals("Bella", getResult.getName());
        CartItemDto getResult2 = items3.get(0);
        assertEquals("User {}: Position {} aus dem Warenkorb entfernt", getResult2.getName());
        assertEquals(1, getResult2.getQuantity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertEquals(2L, getResult2.getItemId().longValue());
        assertEquals(5, getResult.getQuantity());
        assertEquals(CartItemType.DISH, getResult.getType());
        assertEquals(new BigDecimal("11.5"), getResult.getLineTotal());
        assertEquals(new BigDecimal("13.80"), actualRemoveItemResult.getTotalPrice());
        assertEquals(new BigDecimal("2.3"), getResult2.getLineTotal());
        assertEquals(new BigDecimal("2.3"), getResult.getUnitPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity() {
        // Arrange
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

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
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 1));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity2() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(99L);
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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(99L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 0));
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity3() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(99L);
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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(99L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        ShoppingCartMapper mapper = mock(ShoppingCartMapper.class);
        CartDto cartDto = new CartDto();
        when(mapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, mock(DishRepository.class), mock(MenuRepository.class), mapper);

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 0);

        // Assert
        verify(mapper).toDto(isA(ShoppingCart.class));
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        assertSame(cartDto, actualUpdateItemQuantityResult);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity4() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 1));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity5() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 1));
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Given {@link Location#Location()} PostalCode is one.
     *   <li>Then return Id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName(
            "Test updateItemQuantity(Long, Long, int); given Location() PostalCode is one; then return Id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenLocationPostalCodeIsOne_thenReturnIdLongValueIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        assertEquals(1L, actualUpdateItemQuantityResult.getId().longValue());
        assertEquals(1L, actualUpdateItemQuantityResult.getUserId().longValue());
        assertTrue(actualUpdateItemQuantityResult.getItems().isEmpty());
        assertEquals(new BigDecimal("0.00"), actualUpdateItemQuantityResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartMapper}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName(
            "Test updateItemQuantity(Long, Long, int); given ShoppingCartMapper; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenShoppingCartMapper_thenThrowResponseStatusException() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 1));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Given {@link ShoppingCartRepository}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); given ShoppingCartRepository")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenShoppingCartRepository() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 1));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName(
            "Test updateItemQuantity(Long, Long, int); given User() Email is 'john.smith@example.org'; then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenUserEmailIsJohnSmithExampleOrg_thenReturnCartDto() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(2L);
        user2.setNachname("User {}: Position {} auf Menge {} gesetzt");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.ADMIN);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("User {}: Position {} auf Menge {} gesetzt");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);

        Location location3 = new Location();
        location3.setDescription("User {}: Position {} auf Menge {} gesetzt");
        location3.setId(2L);
        location3.setPostalCode(99L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("User {}: Position {} auf Menge {} gesetzt");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setHausnummer("Hausnummer");
        owner3.setId(2L);
        owner3.setNachname("User {}: Position {} auf Menge {} gesetzt");
        owner3.setOrt("Ort");
        owner3.setPasswort("Passwort");
        owner3.setPlz("Plz");
        owner3.setRole(Role.ADMIN);
        owner3.setStrasse("Strasse");
        owner3.setTelefon("Telefon");
        owner3.setVorname("User {}: Position {} auf Menge {} gesetzt");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(2L);
        restaurant3.setLocation(location3);
        restaurant3.setName("User {}: Position {} auf Menge {} gesetzt");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(false);
        dish2.setDescription("User {}: Position {} auf Menge {} gesetzt");
        dish2.setId(2L);
        dish2.setName("User {}: Position {} auf Menge {} gesetzt");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("User {}: Position {} auf Menge {} gesetzt");
        location4.setId(2L);
        location4.setPostalCode(99L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("User {}: Position {} auf Menge {} gesetzt");

        User owner4 = new User();
        owner4.setEmail("john.smith@example.org");
        owner4.setHausnummer("Hausnummer");
        owner4.setId(2L);
        owner4.setNachname("User {}: Position {} auf Menge {} gesetzt");
        owner4.setOrt("Ort");
        owner4.setPasswort("Passwort");
        owner4.setPlz("Plz");
        owner4.setRole(Role.ADMIN);
        owner4.setStrasse("Strasse");
        owner4.setTelefon("Telefon");
        owner4.setVorname("User {}: Position {} auf Menge {} gesetzt");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(2L);
        restaurant4.setLocation(location4);
        restaurant4.setName("User {}: Position {} auf Menge {} gesetzt");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(false);
        menu2.setDescription("User {}: Position {} auf Menge {} gesetzt");
        menu2.setId(2L);
        menu2.setName("User {}: Position {} auf Menge {} gesetzt");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(2L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user3);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user4);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualUpdateItemQuantityResult);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return {@link CartDto#CartDto()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return CartDto()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_thenReturnCartDto() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any()))
                .thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CartDto cartDto = new CartDto();
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
        assertSame(cartDto, actualUpdateItemQuantityResult);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return Items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return Items size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_thenReturnItemsSizeIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("User {}: Position {} auf Menge {} gesetzt");
        user3.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        user3.setPlz("User {}: Position {} auf Menge {} gesetzt");
        user3.setRole(Role.USER);
        user3.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        user3.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        user3.setVorname("Bella");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(1L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user3);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("User {}: Position {} auf Menge {} gesetzt");
        owner3.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        owner3.setPlz("User {}: Position {} auf Menge {} gesetzt");
        owner3.setRole(Role.USER);
        owner3.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        owner3.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("User {}: Position {} auf Menge {} gesetzt");
        owner4.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        owner4.setPlz("User {}: Position {} auf Menge {} gesetzt");
        owner4.setRole(Role.USER);
        owner4.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        owner4.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(true);
        menu2.setDescription("The characteristics of someone or something");
        menu2.setId(1L);
        menu2.setName("Bella");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(1L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(1);

        ArrayList<CartItem> items2 = new ArrayList<>();
        items2.add(cartItem2);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items2);
        shoppingCart2.setUser(user4);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user5);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> items3 = actualUpdateItemQuantityResult.getItems();
        assertEquals(1, items3.size());
        CartItemDto getResult = items3.get(0);
        assertEquals("Bella", getResult.getName());
        assertEquals(1, getResult.getQuantity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(new BigDecimal("2.3"), getResult.getLineTotal());
        assertEquals(new BigDecimal("2.30"), actualUpdateItemQuantityResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return Items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return Items size is two")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_thenReturnItemsSizeIsTwo() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(1L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(1);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("User {}: Position {} auf Menge {} gesetzt");
        user3.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        user3.setPlz("User {}: Position {} auf Menge {} gesetzt");
        user3.setRole(Role.USER);
        user3.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        user3.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        user3.setVorname("Bella");

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(1L);
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user3);

        Location location3 = new Location();
        location3.setDescription("The characteristics of someone or something");
        location3.setId(1L);
        location3.setPostalCode(1L);
        location3.setRestaurant(new Restaurant());
        location3.setStreetName("Bella");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        owner3.setId(1L);
        owner3.setNachname("Bella");
        owner3.setOrt("User {}: Position {} auf Menge {} gesetzt");
        owner3.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        owner3.setPlz("User {}: Position {} auf Menge {} gesetzt");
        owner3.setRole(Role.USER);
        owner3.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        owner3.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        owner3.setVorname("Bella");

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId(1L);
        restaurant3.setLocation(location3);
        restaurant3.setName("Bella");
        restaurant3.setOwner(owner3);

        Dish dish2 = new Dish();
        dish2.setAvailable(true);
        dish2.setDescription("The characteristics of someone or something");
        dish2.setId(1L);
        dish2.setName("Bella");
        dish2.setPrice(new BigDecimal("2.3"));
        dish2.setRestaurant(restaurant3);

        Location location4 = new Location();
        location4.setDescription("The characteristics of someone or something");
        location4.setId(1L);
        location4.setPostalCode(1L);
        location4.setRestaurant(new Restaurant());
        location4.setStreetName("Bella");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setHausnummer("User {}: Position {} auf Menge {} gesetzt");
        owner4.setId(1L);
        owner4.setNachname("Bella");
        owner4.setOrt("User {}: Position {} auf Menge {} gesetzt");
        owner4.setPasswort("User {}: Position {} auf Menge {} gesetzt");
        owner4.setPlz("User {}: Position {} auf Menge {} gesetzt");
        owner4.setRole(Role.USER);
        owner4.setStrasse("User {}: Position {} auf Menge {} gesetzt");
        owner4.setTelefon("User {}: Position {} auf Menge {} gesetzt");
        owner4.setVorname("Bella");

        Restaurant restaurant4 = new Restaurant();
        restaurant4.setId(1L);
        restaurant4.setLocation(location4);
        restaurant4.setName("Bella");
        restaurant4.setOwner(owner4);

        Menu menu2 = new Menu();
        menu2.setAvailable(true);
        menu2.setDescription("The characteristics of someone or something");
        menu2.setId(1L);
        menu2.setName("Bella");
        menu2.setPrice(new BigDecimal("2.3"));
        menu2.setRestaurant(restaurant4);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setDish(dish2);
        cartItem2.setId(1L);
        cartItem2.setMenu(menu2);
        cartItem2.setQuantity(1);

        User user4 = new User();
        user4.setEmail("john.smith@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(2L);
        user4.setNachname("User {}: Position {} auf Menge {} gesetzt");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.ADMIN);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("User {}: Position {} auf Menge {} gesetzt");

        ShoppingCart cart3 = new ShoppingCart();
        cart3.setId(2L);
        cart3.setItems(new ArrayList<>());
        cart3.setUser(user4);

        Location location5 = new Location();
        location5.setDescription("User {}: Position {} auf Menge {} gesetzt");
        location5.setId(2L);
        location5.setPostalCode(99L);
        location5.setRestaurant(new Restaurant());
        location5.setStreetName("User {}: Position {} auf Menge {} gesetzt");

        User owner5 = new User();
        owner5.setEmail("john.smith@example.org");
        owner5.setHausnummer("Hausnummer");
        owner5.setId(2L);
        owner5.setNachname("User {}: Position {} auf Menge {} gesetzt");
        owner5.setOrt("Ort");
        owner5.setPasswort("Passwort");
        owner5.setPlz("Plz");
        owner5.setRole(Role.ADMIN);
        owner5.setStrasse("Strasse");
        owner5.setTelefon("Telefon");
        owner5.setVorname("User {}: Position {} auf Menge {} gesetzt");

        Restaurant restaurant5 = new Restaurant();
        restaurant5.setId(2L);
        restaurant5.setLocation(location5);
        restaurant5.setName("User {}: Position {} auf Menge {} gesetzt");
        restaurant5.setOwner(owner5);

        Dish dish3 = new Dish();
        dish3.setAvailable(false);
        dish3.setDescription("User {}: Position {} auf Menge {} gesetzt");
        dish3.setId(2L);
        dish3.setName("User {}: Position {} auf Menge {} gesetzt");
        dish3.setPrice(new BigDecimal("2.3"));
        dish3.setRestaurant(restaurant5);

        Location location6 = new Location();
        location6.setDescription("User {}: Position {} auf Menge {} gesetzt");
        location6.setId(2L);
        location6.setPostalCode(99L);
        location6.setRestaurant(new Restaurant());
        location6.setStreetName("User {}: Position {} auf Menge {} gesetzt");

        User owner6 = new User();
        owner6.setEmail("john.smith@example.org");
        owner6.setHausnummer("Hausnummer");
        owner6.setId(2L);
        owner6.setNachname("User {}: Position {} auf Menge {} gesetzt");
        owner6.setOrt("Ort");
        owner6.setPasswort("Passwort");
        owner6.setPlz("Plz");
        owner6.setRole(Role.ADMIN);
        owner6.setStrasse("Strasse");
        owner6.setTelefon("Telefon");
        owner6.setVorname("User {}: Position {} auf Menge {} gesetzt");

        Restaurant restaurant6 = new Restaurant();
        restaurant6.setId(2L);
        restaurant6.setLocation(location6);
        restaurant6.setName("User {}: Position {} auf Menge {} gesetzt");
        restaurant6.setOwner(owner6);

        Menu menu3 = new Menu();
        menu3.setAvailable(false);
        menu3.setDescription("User {}: Position {} auf Menge {} gesetzt");
        menu3.setId(2L);
        menu3.setName("User {}: Position {} auf Menge {} gesetzt");
        menu3.setPrice(new BigDecimal("2.3"));
        menu3.setRestaurant(restaurant6);

        CartItem cartItem3 = new CartItem();
        cartItem3.setCart(cart3);
        cartItem3.setDish(dish3);
        cartItem3.setId(2L);
        cartItem3.setMenu(menu3);
        cartItem3.setQuantity(99);

        ArrayList<CartItem> items2 = new ArrayList<>();
        items2.add(cartItem3);
        items2.add(cartItem2);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setHausnummer("Hausnummer");
        user5.setId(1L);
        user5.setNachname("Bella");
        user5.setOrt("Ort");
        user5.setPasswort("Passwort");
        user5.setPlz("Plz");
        user5.setRole(Role.USER);
        user5.setStrasse("Strasse");
        user5.setTelefon("Telefon");
        user5.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(items2);
        shoppingCart2.setUser(user5);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setHausnummer("Hausnummer");
        user6.setId(1L);
        user6.setNachname("Bella");
        user6.setOrt("Ort");
        user6.setPasswort("Passwort");
        user6.setPlz("Plz");
        user6.setRole(Role.USER);
        user6.setStrasse("Strasse");
        user6.setTelefon("Telefon");
        user6.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user6);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> items3 = actualUpdateItemQuantityResult.getItems();
        assertEquals(2, items3.size());
        CartItemDto getResult = items3.get(1);
        assertEquals("Bella", getResult.getName());
        CartItemDto getResult2 = items3.get(0);
        assertEquals("User {}: Position {} auf Menge {} gesetzt", getResult2.getName());
        assertEquals(1, getResult.getQuantity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getItemId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertEquals(2L, getResult2.getItemId().longValue());
        assertEquals(99, getResult2.getQuantity());
        assertEquals(CartItemType.DISH, getResult.getType());
        assertEquals(new BigDecimal("2.3"), getResult.getLineTotal());
        assertEquals(new BigDecimal("2.3"), getResult.getUnitPrice());
        assertEquals(new BigDecimal("227.7"), getResult2.getLineTotal());
        assertEquals(new BigDecimal("230.00"), actualUpdateItemQuantityResult.getTotalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>When minus one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); when minus one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_whenMinusOne() {
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

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, -1));
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>When zero.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); when zero")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_whenZero() {
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUser(user);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user2);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 0));
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>When zero.
     *   <li>Then return Id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName(
            "Test updateItemQuantity(Long, Long, int); when zero; then return Id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_whenZero_thenReturnIdLongValueIsOne() {
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

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Location location = new Location();
        location.setDescription("The characteristics of someone or something");
        location.setId(1L);
        location.setPostalCode(99L);
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

        Dish dish = new Dish();
        dish.setAvailable(true);
        dish.setDescription("The characteristics of someone or something");
        dish.setId(1L);
        dish.setName("Bella");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setRestaurant(restaurant);

        Location location2 = new Location();
        location2.setDescription("The characteristics of someone or something");
        location2.setId(1L);
        location2.setPostalCode(99L);
        location2.setRestaurant(new Restaurant());
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

        Menu menu = new Menu();
        menu.setAvailable(true);
        menu.setDescription("The characteristics of someone or something");
        menu.setId(1L);
        menu.setName("Bella");
        menu.setPrice(new BigDecimal("2.3"));
        menu.setRestaurant(restaurant2);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDish(dish);
        cartItem.setId(1L);
        cartItem.setMenu(menu);
        cartItem.setQuantity(99);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(items);
        shoppingCart.setUser(user2);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setHausnummer("Hausnummer");
        user3.setId(1L);
        user3.setNachname("Bella");
        user3.setOrt("Ort");
        user3.setPasswort("Passwort");
        user3.setPlz("Plz");
        user3.setRole(Role.USER);
        user3.setStrasse("Strasse");
        user3.setTelefon("Telefon");
        user3.setVorname("Bella");

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(1L);
        shoppingCart2.setItems(new ArrayList<>());
        shoppingCart2.setUser(user3);

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setHausnummer("Hausnummer");
        user4.setId(1L);
        user4.setNachname("Bella");
        user4.setOrt("Ort");
        user4.setPasswort("Passwort");
        user4.setPlz("Plz");
        user4.setRole(Role.USER);
        user4.setStrasse("Strasse");
        user4.setTelefon("Telefon");
        user4.setVorname("Bella");
        Optional<User> ofResult2 = Optional.of(user4);

        UserRepository userRepo = mock(UserRepository.class);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 0);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        assertEquals(1L, actualUpdateItemQuantityResult.getId().longValue());
        assertEquals(1L, actualUpdateItemQuantityResult.getUserId().longValue());
        assertTrue(actualUpdateItemQuantityResult.getItems().isEmpty());
        assertEquals(new BigDecimal("0.00"), actualUpdateItemQuantityResult.getTotalPrice());
    }
}
