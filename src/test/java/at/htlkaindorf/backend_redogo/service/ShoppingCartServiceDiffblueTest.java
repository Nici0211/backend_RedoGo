package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import at.htlkaindorf.backend_redogo.repository.CartItemRepository;
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
    private CartItemRepository cartItemRepository;

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
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); given quantity is greater than maximum")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenQuantityIsGreaterThanMaximum() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(100);
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
    @DisplayName("Test addItem(Long, AddToCartRequestDto); given type is null")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenTypeIsNull() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        ShoppingCart cart = new ShoppingCart(user);
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(Optional.of(cart));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> shoppingCartService.addItem(1L, request));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); given cart contains item without dish")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenCartContainsItemWithoutDish() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        ShoppingCart cart = new ShoppingCart(user);
        cart.setId(1L);
        cart.setItems(new ArrayList<>());

        Menu menu = new Menu();
        menu.setId(2L);
        menu.setName("Bella Menü");
        menu.setPrice(new BigDecimal("4.5"));
        menu.setAvailable(true);

        CartItem existingItem = new CartItem();
        existingItem.setId(1L);
        existingItem.setCart(cart);
        existingItem.setMenu(menu);
        existingItem.setQuantity(1);
        cart.getItems().add(existingItem);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(Optional.of(cart));
        when(shoppingCartRepository.saveAndFlush(Mockito.<ShoppingCart>any())).thenReturn(cart);

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Bella Gericht");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setAvailable(true);
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(dish));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act
        shoppingCartService.addItem(1L, request);

        // Assert
        verify(dishRepository).findById(1L);
        verify(shoppingCartRepository).findByUserId(1L);
        verify(shoppingCartRepository).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepository).findById(1L);
        assertEquals(2, cart.getItems().size());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); given quantity is negative")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenQuantityIsNegative() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> shoppingCartService.updateItemQuantity(1L, 1L, -1));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); given quantity is greater than maximum")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenQuantityIsGreaterThanMaximum() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> shoppingCartService.updateItemQuantity(1L, 1L, 100));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); given new quantity is greater than maximum")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenNewQuantityIsGreaterThanMaximum() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        ShoppingCart cart = new ShoppingCart(user);
        cart.setId(1L);
        cart.setItems(new ArrayList<>());

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Bella Gericht");
        dish.setPrice(new BigDecimal("2.3"));
        dish.setAvailable(true);

        CartItem item = new CartItem();
        item.setId(1L);
        item.setCart(cart);
        item.setDish(dish);
        item.setQuantity(99);
        cart.getItems().add(item);

        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(Optional.of(cart));
        when(dishRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(dish));

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setType(CartItemType.DISH);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.addItem(1L, request));
        verify(dishRepository).findById(1L);
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
    void testGetCart() {
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
    void testGetCart2() {
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
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart3() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
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
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart4() {
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
    void testGetCart5() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
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
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.getCart(Long)"})
    void testGetCart6() {
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

        ShoppingCartMapper mapper = mock(ShoppingCartMapper.class);
        when(mapper.emptyCart(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo,
                        mock(CartItemRepository.class),
                        userRepo,
                        mock(DishRepository.class),
                        mock(MenuRepository.class),
                        mapper);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.getCart(1L));
        verify(mapper).emptyCart(1L);
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code john.smith@example.org}.
     *   <li>Then return items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName(
            "Test getCart(Long); given User() Email is 'john.smith@example.org'; then return items size is two")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> itemsResult = actualCart.items();
        assertEquals(2, itemsResult.size());
        CartItemDto getResult = itemsResult.get(1);
        assertEquals("Bella", getResult.name());
        CartItemDto getResult2 = itemsResult.get(0);
        assertEquals("Name", getResult2.name());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        assertEquals(2, getResult.quantity());
        assertEquals(2L, getResult2.id().longValue());
        assertEquals(2L, getResult2.itemId().longValue());
        assertEquals(CartItemType.DISH, getResult.type());
        BigDecimal expectedUnitPriceResult = new BigDecimal("2.3");
        assertEquals(expectedUnitPriceResult, getResult.unitPrice());
        BigDecimal expectedLineTotalResult = new BigDecimal("4.6");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("9.20");
        assertEquals(expectedTotalPriceResult, actualCart.totalPrice());
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
     *   <li>Then return id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long); then return id longValue is one")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        assertEquals(1L, actualCart.id().longValue());
        assertEquals(1L, actualCart.userId().longValue());
        assertTrue(actualCart.items().isEmpty());
        BigDecimal expectedTotalPriceResult = new BigDecimal("0.00");
        assertEquals(expectedTotalPriceResult, actualCart.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#getCart(Long)}.
     *
     * <ul>
     *   <li>Then return items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#getCart(Long)}
     */
    @Test
    @DisplayName("Test getCart(Long); then return items size is one")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualCart = shoppingCartService.getCart(1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
        List<CartItemDto> itemsResult = actualCart.items();
        assertEquals(1, itemsResult.size());
        CartItemDto getResult = itemsResult.get(0);
        assertEquals("Bella", getResult.name());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        BigDecimal expectedTotalPriceResult = new BigDecimal("4.60");
        assertEquals(expectedTotalPriceResult, actualCart.totalPrice());
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
    void testAddItem4() {
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
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem5() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
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
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem7() {
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
     * <ul>
     *   <li>Given {@link AddToCartRequestDto} (default constructor) Type is {@code DISH}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given AddToCartRequestDto (default constructor) Type is 'DISH'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenAddToCartRequestDtoTypeIsDish() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
        when(mapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        AddToCartRequestDto request = new AddToCartRequestDto();
        request.setItemId(2L);
        request.setQuantity(99);
        request.setType(CartItemType.DISH);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, mock(CartItemRepository.class), userRepo, dishRepo, menuRepo, mapper);
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());
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
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given MenuRepository findById(Object) return of Menu()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenMenuRepositoryFindByIdReturnOfMenu() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
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
     *   <li>Given {@link ShoppingCartRepository} {@link ShoppingCartRepository#findByUserId(Long)}
     *       return empty.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given ShoppingCartRepository findByUserId(Long) return empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenShoppingCartRepositoryFindByUserIdReturnEmpty() {
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
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items, new BigDecimal("2.3"));
        when(mapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo,
                        mock(CartItemRepository.class),
                        userRepo,
                        mock(DishRepository.class),
                        menuRepo,
                        mapper);

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
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'john.smith@example.org'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserEmailIsJohnSmithExampleOrg() {
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
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
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
     *   <li>Then return items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'john.smith@example.org'; then return items size is two")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

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
        List<CartItemDto> itemsResult = actualAddItemResult.items();
        assertEquals(2, itemsResult.size());
        CartItemDto getResult = itemsResult.get(1);
        assertEquals("Bella", getResult.name());
        CartItemDto getResult2 = itemsResult.get(0);
        assertEquals("User {}: {}x {} {} im Warenkorb", getResult2.name());
        assertEquals(1, getResult.quantity());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        assertEquals(2L, getResult2.id().longValue());
        assertEquals(2L, getResult2.itemId().longValue());
        assertEquals(99, getResult2.quantity());
        assertEquals(CartItemType.DISH, getResult.type());
        BigDecimal expectedLineTotalResult = new BigDecimal("2.3");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedUnitPriceResult = new BigDecimal("2.3");
        assertEquals(expectedUnitPriceResult, getResult.unitPrice());
        BigDecimal expectedLineTotalResult2 = new BigDecimal("227.7");
        assertEquals(expectedLineTotalResult2, getResult2.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("230.00");
        assertEquals(expectedTotalPriceResult, actualAddItemResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code prof.einstein@example.org}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Email is 'prof.einstein@example.org'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserEmailIsProfEinsteinExampleOrg() {
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
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
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
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName(
            "Test addItem(Long, AddToCartRequestDto); given User() Hausnummer is 'User {}: {}x {} {} im Warenkorb'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.addItem(Long, AddToCartRequestDto)"})
    void testAddItem_givenUserHausnummerIsUserXImWarenkorb() {
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
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
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
                        mock(CartItemRepository.class),
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
     *   <li>Then return id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); then return id longValue is one")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

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
        assertEquals(1L, actualAddItemResult.id().longValue());
        assertEquals(1L, actualAddItemResult.userId().longValue());
        assertTrue(actualAddItemResult.items().isEmpty());
        BigDecimal expectedTotalPriceResult = new BigDecimal("0.00");
        assertEquals(expectedTotalPriceResult, actualAddItemResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}.
     *
     * <ul>
     *   <li>Then return items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#addItem(Long, AddToCartRequestDto)}
     */
    @Test
    @DisplayName("Test addItem(Long, AddToCartRequestDto); then return items size is one")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

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
        List<CartItemDto> itemsResult = actualAddItemResult.items();
        assertEquals(1, itemsResult.size());
        CartItemDto getResult = itemsResult.get(0);
        assertEquals("Bella", getResult.name());
        assertEquals(1, getResult.quantity());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        BigDecimal expectedLineTotalResult = new BigDecimal("2.3");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("2.30");
        assertEquals(expectedTotalPriceResult, actualAddItemResult.totalPrice());
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
        when(shoppingCartRepository.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(cartItemRepository).delete(Mockito.<CartItem>any());
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(cartItemRepository).delete(isA(CartItem.class));
        verify(shoppingCartRepository).save(isA(ShoppingCart.class));
        assertSame(cartDto, actualRemoveItemResult);
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

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.save(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doNothing().when(cartItemRepo).delete(Mockito.<CartItem>any());
        UserRepository userRepo = mock(UserRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
        verify(cartRepo).save(isA(ShoppingCart.class));
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
        when(shoppingCartRepository.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(cartItemRepository).delete(Mockito.<CartItem>any());
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any())).thenReturn(cartDto);

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(cartItemRepository).delete(isA(CartItem.class));
        verify(shoppingCartRepository).save(isA(ShoppingCart.class));
        assertSame(cartDto, actualRemoveItemResult);
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
    void testRemoveItem4() {
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
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(cartItemRepository)
                .delete(Mockito.<CartItem>any());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(cartItemRepository).delete(isA(CartItem.class));
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
    void testRemoveItem5() {
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
        when(shoppingCartRepository.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(shoppingCartRepository.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(cartItemRepository).delete(Mockito.<CartItem>any());
        when(shoppingCartMapper.toDto(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartMapper).toDto(isA(ShoppingCart.class));
        verify(shoppingCartRepository).findByUserId(1L);
        verify(cartItemRepository).delete(isA(CartItem.class));
        verify(shoppingCartRepository).save(isA(ShoppingCart.class));
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link CartItemRepository}.
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given CartItemRepository; then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenCartItemRepository_thenThrowResponseStatusException() {
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

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> shoppingCartService.removeItem(1L, 1L));
        verify(shoppingCartRepository).findByUserId(1L);
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Given {@link Location#Location()} PostalCode is two.
     *   <li>Then return items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName(
            "Test removeItem(Long, Long); given Location() PostalCode is two; then return items size is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.removeItem(Long, Long)"})
    void testRemoveItem_givenLocationPostalCodeIsTwo_thenReturnItemsSizeIsOne() {
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
        location3.setPostalCode(2L);
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
        location4.setPostalCode(2L);
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

        ShoppingCartRepository cartRepo = mock(ShoppingCartRepository.class);
        when(cartRepo.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doNothing().when(cartItemRepo).delete(Mockito.<CartItem>any());
        UserRepository userRepo = mock(UserRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
        verify(cartRepo).save(isA(ShoppingCart.class));
        List<CartItemDto> itemsResult = actualRemoveItemResult.items();
        assertEquals(1, itemsResult.size());
        CartItemDto getResult = itemsResult.get(0);
        assertEquals("User {}: Position {} aus dem Warenkorb entfernt", getResult.name());
        assertEquals(2, getResult.quantity());
        assertEquals(2L, getResult.id().longValue());
        assertEquals(2L, getResult.itemId().longValue());
        assertEquals(CartItemType.DISH, getResult.type());
        BigDecimal expectedUnitPriceResult = new BigDecimal("2.3");
        assertEquals(expectedUnitPriceResult, getResult.unitPrice());
        BigDecimal expectedLineTotalResult = new BigDecimal("4.6");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("4.60");
        assertEquals(expectedTotalPriceResult, actualRemoveItemResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#removeItem(Long, Long)}.
     *
     * <ul>
     *   <li>Then return id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#removeItem(Long, Long)}
     */
    @Test
    @DisplayName("Test removeItem(Long, Long); then return id longValue is one")
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
        when(cartRepo.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doNothing().when(cartItemRepo).delete(Mockito.<CartItem>any());
        UserRepository userRepo = mock(UserRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualRemoveItemResult = shoppingCartService.removeItem(1L, 1L);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
        verify(cartRepo).save(isA(ShoppingCart.class));
        assertEquals(1L, actualRemoveItemResult.id().longValue());
        assertEquals(1L, actualRemoveItemResult.userId().longValue());
        assertTrue(actualRemoveItemResult.items().isEmpty());
        BigDecimal expectedTotalPriceResult = new BigDecimal("0.00");
        assertEquals(expectedTotalPriceResult, actualRemoveItemResult.totalPrice());
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
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
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
        ArrayList<CartItemDto> items2 = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, 1L, items2, new BigDecimal("2.3"));
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
        when(cartRepo.save(Mockito.<ShoppingCart>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doNothing().when(cartItemRepo).delete(Mockito.<CartItem>any());

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
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 0));
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
        verify(userRepo).findById(1L);
        verify(cartRepo).save(isA(ShoppingCart.class));
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
    void testUpdateItemQuantity6() {
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
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(cartItemRepo)
                .delete(Mockito.<CartItem>any());

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
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 0));
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
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
    void testUpdateItemQuantity7() {
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
     *   <li>Given {@link ShoppingCartRepository} {@link ShoppingCartRepository#save(Object)} return
     *       {@link ShoppingCart#ShoppingCart()}.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName(
            "Test updateItemQuantity(Long, Long, int); given ShoppingCartRepository save(Object) return ShoppingCart()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_givenShoppingCartRepositorySaveReturnShoppingCart() {
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
        when(cartRepo.save(Mockito.<ShoppingCart>any())).thenReturn(shoppingCart2);
        when(cartRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        doNothing().when(cartItemRepo).delete(Mockito.<CartItem>any());

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
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 0);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartItemRepo).delete(isA(CartItem.class));
        verify(userRepo).findById(1L);
        verify(cartRepo).save(isA(ShoppingCart.class));
        assertEquals(1L, actualUpdateItemQuantityResult.id().longValue());
        assertEquals(1L, actualUpdateItemQuantityResult.userId().longValue());
        assertTrue(actualUpdateItemQuantityResult.items().isEmpty());
        BigDecimal expectedTotalPriceResult = new BigDecimal("0.00");
        assertEquals(expectedTotalPriceResult, actualUpdateItemQuantityResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return id longValue is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return id longValue is one")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"CartDto ShoppingCartService.updateItemQuantity(Long, Long, int)"})
    void testUpdateItemQuantity_thenReturnIdLongValueIsOne() {
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        assertEquals(1L, actualUpdateItemQuantityResult.id().longValue());
        assertEquals(1L, actualUpdateItemQuantityResult.userId().longValue());
        assertTrue(actualUpdateItemQuantityResult.items().isEmpty());
        BigDecimal expectedTotalPriceResult = new BigDecimal("0.00");
        assertEquals(expectedTotalPriceResult, actualUpdateItemQuantityResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return items size is one.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return items size is one")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> itemsResult = actualUpdateItemQuantityResult.items();
        assertEquals(1, itemsResult.size());
        CartItemDto getResult = itemsResult.get(0);
        assertEquals("Bella", getResult.name());
        assertEquals(1, getResult.quantity());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        BigDecimal expectedLineTotalResult = new BigDecimal("2.3");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("2.30");
        assertEquals(expectedTotalPriceResult, actualUpdateItemQuantityResult.totalPrice());
    }

    /**
     * Test {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}.
     *
     * <ul>
     *   <li>Then return items size is two.
     * </ul>
     *
     * <p>Method under test: {@link ShoppingCartService#updateItemQuantity(Long, Long, int)}
     */
    @Test
    @DisplayName("Test updateItemQuantity(Long, Long, int); then return items size is two")
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act
        CartDto actualUpdateItemQuantityResult = shoppingCartService.updateItemQuantity(1L, 1L, 1);

        // Assert
        verify(cartRepo).findByUserId(1L);
        verify(cartRepo).saveAndFlush(isA(ShoppingCart.class));
        verify(userRepo).findById(1L);
        List<CartItemDto> itemsResult = actualUpdateItemQuantityResult.items();
        assertEquals(2, itemsResult.size());
        CartItemDto getResult = itemsResult.get(1);
        assertEquals("Bella", getResult.name());
        CartItemDto getResult2 = itemsResult.get(0);
        assertEquals("User {}: Position {} auf Menge {} gesetzt", getResult2.name());
        assertEquals(1, getResult.quantity());
        assertEquals(1L, getResult.id().longValue());
        assertEquals(1L, getResult.itemId().longValue());
        assertEquals(2L, getResult2.id().longValue());
        assertEquals(2L, getResult2.itemId().longValue());
        assertEquals(99, getResult2.quantity());
        assertEquals(CartItemType.DISH, getResult.type());
        BigDecimal expectedLineTotalResult = new BigDecimal("2.3");
        assertEquals(expectedLineTotalResult, getResult.lineTotal());
        BigDecimal expectedUnitPriceResult = new BigDecimal("2.3");
        assertEquals(expectedUnitPriceResult, getResult.unitPrice());
        BigDecimal expectedLineTotalResult2 = new BigDecimal("227.7");
        assertEquals(expectedLineTotalResult2, getResult2.lineTotal());
        BigDecimal expectedTotalPriceResult = new BigDecimal("230.00");
        assertEquals(expectedTotalPriceResult, actualUpdateItemQuantityResult.totalPrice());
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
        CartItemRepository cartItemRepo = mock(CartItemRepository.class);
        DishRepository dishRepo = mock(DishRepository.class);
        MenuRepository menuRepo = mock(MenuRepository.class);

        ShoppingCartService shoppingCartService =
                new ShoppingCartService(
                        cartRepo, cartItemRepo, userRepo, dishRepo, menuRepo, new ShoppingCartMapperImpl());

        // Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> shoppingCartService.updateItemQuantity(1L, 1L, 0));
        verify(cartRepo).findByUserId(1L);
        verify(userRepo).findById(1L);
    }
}
