package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.CreateDishRequestDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.CartItemRepository;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * US09: Als Restaurantinhaber möchte ich neue Gerichte zur Speisekarte meines Restaurants hinzufügen.
 * Nur Benutzer mit Rolle ADMIN dürfen das, und nur für Restaurants, deren Inhaber sie sind.
 * US11: Als Restaurantinhaber möchte ich Gerichte von der Speisekarte meines Restaurants löschen können.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDishService {

    private final UserRepository userRepo;
    private final RestaurantRepository restaurantRepo;
    private final DishRepository dishRepo;
    private final CartItemRepository cartItemRepo;
    private final MenuRepository menuRepo;
    private final RestaurantMapper restaurantMapper;

    /** Die Restaurants, die dem Admin gehören (für die Auswahl im Frontend). */
    @Transactional(readOnly = true)
    public List<RestaurantDto> getOwnRestaurants(Long adminId) {
        User admin = requireAdmin(adminId);
        return restaurantMapper.toDto(restaurantRepo.findByOwnerIdOrderByNameAsc(admin.getId()));
    }

    @Transactional
    public MenuCardEntryDto addDish(Long adminId, Long restaurantId, CreateDishRequestDto request) {
        User admin = requireAdmin(adminId);
        Restaurant restaurant = requireOwnRestaurant(admin, restaurantId);

        String name = request.getName().trim();
        if (dishRepo.existsByRestaurantIdAndNameIgnoreCase(restaurantId, name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ein Gericht mit dem Namen \"" + name + "\" gibt es in diesem Restaurant bereits");
        }

        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(blankToNull(request.getDescription()));
        dish.setPrice(request.getPrice());
        dish.setAvailable(request.getAvailable() == null || request.getAvailable());
        dish.setRestaurant(restaurant);
        Dish saved = dishRepo.save(dish);

        log.info("Admin {} hat Gericht '{}' (ID {}) zu Restaurant {} hinzugefügt", adminId, name, saved.getId(), restaurantId);
        return new MenuCardEntryDto(saved.getId(), saved.getName(), saved.getDescription(), saved.getPrice(), saved.isAvailable());
    }

    /**
     * US11: Löscht ein Gericht aus der Speisekarte. Das Gericht muss zum angegebenen Restaurant gehören.
     * Positionen in Warenkörben, die dieses Gericht enthalten, werden mit entfernt.
     */
    @Transactional
    public void deleteDish(Long adminId, Long restaurantId, Long dishId) {
        User admin = requireAdmin(adminId);
        requireOwnRestaurant(admin, restaurantId);

        Dish dish = dishRepo.findById(dishId)
                .filter(d -> d.getRestaurant().getId().equals(restaurantId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Gericht mit der ID " + dishId + " wurde in diesem Restaurant nicht gefunden"));

        String name = dish.getName();
        cartItemRepo.deleteByDishId(dishId);
        dishRepo.deleteById(dishId);
        dishRepo.flush();

        log.info("Admin {} hat Gericht '{}' (ID {}) von Restaurant {} gelöscht", adminId, name, dishId, restaurantId);
    }

    /** Legt ein Menü (Kombi-Angebot) an - gleiche Regeln wie bei {@link #addDish}. */
    @Transactional
    public MenuCardEntryDto addMenu(Long adminId, Long restaurantId, CreateDishRequestDto request) {
        User admin = requireAdmin(adminId);
        Restaurant restaurant = requireOwnRestaurant(admin, restaurantId);

        String name = request.getName().trim();
        if (menuRepo.existsByRestaurantIdAndNameIgnoreCase(restaurantId, name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ein Menü mit dem Namen \"" + name + "\" gibt es in diesem Restaurant bereits");
        }

        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(blankToNull(request.getDescription()));
        menu.setPrice(request.getPrice());
        menu.setAvailable(request.getAvailable() == null || request.getAvailable());
        menu.setRestaurant(restaurant);
        Menu saved = menuRepo.save(menu);

        log.info("Admin {} hat Menü '{}' (ID {}) zu Restaurant {} hinzugefügt", adminId, name, saved.getId(), restaurantId);
        return new MenuCardEntryDto(saved.getId(), saved.getName(), saved.getDescription(), saved.getPrice(), saved.isAvailable());
    }

    /** US11: Löscht ein Menü samt zugehöriger Warenkorb-Positionen. */
    @Transactional
    public void deleteMenu(Long adminId, Long restaurantId, Long menuId) {
        User admin = requireAdmin(adminId);
        requireOwnRestaurant(admin, restaurantId);

        Menu menu = menuRepo.findById(menuId)
                .filter(m -> m.getRestaurant().getId().equals(restaurantId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Menü mit der ID " + menuId + " wurde in diesem Restaurant nicht gefunden"));

        String name = menu.getName();
        cartItemRepo.deleteByMenuId(menuId);
        menuRepo.deleteById(menuId);
        menuRepo.flush();

        log.info("Admin {} hat Menü '{}' (ID {}) von Restaurant {} gelöscht", adminId, name, menuId, restaurantId);
    }

    private Restaurant requireOwnRestaurant(User admin, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Restaurant mit der ID " + restaurantId + " wurde nicht gefunden"));
        if (restaurant.getOwner() == null || !restaurant.getOwner().getId().equals(admin.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten");
        }
        return restaurant;
    }

    private User requireAdmin(Long adminId) {
        User user = userRepo.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Benutzer mit der ID " + adminId + " wurde nicht gefunden"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Nur Restaurantinhaber (Admin) dürfen die Speisekarte bearbeiten");
        }
        return user;
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
