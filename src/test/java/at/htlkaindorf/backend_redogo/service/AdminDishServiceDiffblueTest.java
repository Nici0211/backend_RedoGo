package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.CreateDishRequestDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Reiner Mockito-Unit-Test für {@link AdminDishService}.
 *
 * <p>Es wird bewusst kein Spring-Kontext gestartet (Repositories sind Interfaces und können nicht als
 * Bean-Klassen in einer {@code @ContextConfiguration} stehen), sondern alle Abhängigkeiten werden
 * gemockt.
 */
@ExtendWith(MockitoExtension.class)
@Tag("ContributionFromDiffblue")
class AdminDishServiceDiffblueTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private AdminDishService adminDishService;

    private static User user(Long id, Role role) {
        return User.builder().id(id).role(role).build();
    }

    private static Restaurant restaurant(Long id, User owner) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Testrestaurant");
        restaurant.setOwner(owner);
        return restaurant;
    }

    private static CreateDishRequestDto request(String name, String description, String price, Boolean available) {
        return new CreateDishRequestDto(name, description, price == null ? null : new BigDecimal(price), available);
    }

    private static void assertStatus(HttpStatus expected, ResponseStatusException ex) {
        assertEquals(expected, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    // ---------------------------------------------------------------- getOwnRestaurants

    @Test
    @DisplayName("getOwnRestaurants: Admin bekommt seine Restaurants als DTOs")
    void testGetOwnRestaurants_whenAdmin_thenReturnMappedRestaurants() {
        User admin = user(1L, Role.ADMIN);
        List<Restaurant> restaurants = List.of(restaurant(10L, admin));
        List<RestaurantDto> dtos = List.of(new RestaurantDto());
        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(restaurantRepository.findByOwnerIdOrderByNameAsc(1L)).thenReturn(restaurants);
        when(restaurantMapper.toDto(restaurants)).thenReturn(dtos);

        List<RestaurantDto> result = adminDishService.getOwnRestaurants(1L);

        assertSame(dtos, result);
        verify(restaurantRepository).findByOwnerIdOrderByNameAsc(1L);
    }

    @Test
    @DisplayName("getOwnRestaurants: unbekannter Benutzer -> 404")
    void testGetOwnRestaurants_whenUserNotFound_thenThrowNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));

        assertStatus(HttpStatus.NOT_FOUND, ex);
        verify(restaurantRepository, never()).findByOwnerIdOrderByNameAsc(any());
    }

    @Test
    @DisplayName("getOwnRestaurants: normaler Benutzer -> 403")
    void testGetOwnRestaurants_whenUserIsNotAdmin_thenThrowForbidden() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L, Role.USER)));

        ResponseStatusException ex =
                assertThrows(ResponseStatusException.class, () -> adminDishService.getOwnRestaurants(1L));

        assertStatus(HttpStatus.FORBIDDEN, ex);
        verify(restaurantRepository, never()).findByOwnerIdOrderByNameAsc(any());
    }

    // ---------------------------------------------------------------- addDish

    @Test
    @DisplayName("addDish: unbekannter Benutzer -> 404")
    void testAddDish_whenUserNotFound_thenThrowNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("Pizza", null, "9.90", null)));

        assertStatus(HttpStatus.NOT_FOUND, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: normaler Benutzer -> 403")
    void testAddDish_whenUserIsNotAdmin_thenThrowForbidden() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L, Role.USER)));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("Pizza", null, "9.90", null)));

        assertStatus(HttpStatus.FORBIDDEN, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: unbekanntes Restaurant -> 404")
    void testAddDish_whenRestaurantNotFound_thenThrowNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L, Role.ADMIN)));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("Pizza", null, "9.90", null)));

        assertStatus(HttpStatus.NOT_FOUND, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: Restaurant ohne Inhaber -> 403")
    void testAddDish_whenRestaurantHasNoOwner_thenThrowForbidden() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L, Role.ADMIN)));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant(5L, null)));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("Pizza", null, "9.90", null)));

        assertStatus(HttpStatus.FORBIDDEN, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: Restaurant gehört einem anderen Admin -> 403")
    void testAddDish_whenRestaurantOwnedByOtherAdmin_thenThrowForbidden() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user(1L, Role.ADMIN)));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant(5L, user(2L, Role.ADMIN))));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("Pizza", null, "9.90", null)));

        assertStatus(HttpStatus.FORBIDDEN, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: Gericht mit gleichem Namen existiert schon -> 409")
    void testAddDish_whenDishNameAlreadyExists_thenThrowConflict() {
        User admin = user(1L, Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant(5L, admin)));
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(5L, "Pizza")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> adminDishService.addDish(1L, 5L, request("  Pizza  ", null, "9.90", null)));

        assertStatus(HttpStatus.CONFLICT, ex);
        verify(dishRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDish: gültige Anfrage -> Gericht wird getrimmt gespeichert und als DTO zurückgegeben")
    void testAddDish_whenValidRequest_thenSaveDishAndReturnEntry() {
        User admin = user(1L, Role.ADMIN);
        Restaurant restaurant = restaurant(5L, admin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant));
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(5L, "Pizza")).thenReturn(false);
        when(dishRepository.save(any(Dish.class))).thenAnswer(invocation -> {
            Dish dish = invocation.getArgument(0);
            dish.setId(42L);
            return dish;
        });

        MenuCardEntryDto result =
                adminDishService.addDish(1L, 5L, request("  Pizza  ", "  Mit Käse  ", "9.90", false));

        ArgumentCaptor<Dish> captor = ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository).save(captor.capture());
        Dish saved = captor.getValue();
        assertEquals("Pizza", saved.getName());
        assertEquals("Mit Käse", saved.getDescription());
        assertEquals(new BigDecimal("9.90"), saved.getPrice());
        assertFalse(saved.isAvailable());
        assertSame(restaurant, saved.getRestaurant());

        assertEquals(42L, result.getId());
        assertEquals("Pizza", result.getName());
        assertEquals("Mit Käse", result.getDescription());
        assertEquals(new BigDecimal("9.90"), result.getPrice());
        assertFalse(result.isAvailable());
    }

    @Test
    @DisplayName("addDish: ohne available und mit leerer Beschreibung -> verfügbar, Beschreibung null")
    void testAddDish_whenAvailableNullAndBlankDescription_thenDefaults() {
        User admin = user(1L, Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant(5L, admin)));
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(5L, "Suppe")).thenReturn(false);
        when(dishRepository.save(any(Dish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuCardEntryDto result = adminDishService.addDish(1L, 5L, request("Suppe", "   ", "4.50", null));

        assertNull(result.getDescription());
        assertTrue(result.isAvailable());
    }

    @Test
    @DisplayName("addDish: available = true bleibt verfügbar, Beschreibung null bleibt null")
    void testAddDish_whenAvailableTrueAndDescriptionNull_thenAvailable() {
        User admin = user(1L, Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(restaurantRepository.findById(5L)).thenReturn(Optional.of(restaurant(5L, admin)));
        when(dishRepository.existsByRestaurantIdAndNameIgnoreCase(5L, "Salat")).thenReturn(false);
        when(dishRepository.save(any(Dish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuCardEntryDto result = adminDishService.addDish(1L, 5L, request("Salat", null, "6.00", true));

        assertNull(result.getDescription());
        assertTrue(result.isAvailable());
    }
}
