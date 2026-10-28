package at.htlkaindorf.backend_redogo.service;

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
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDishService {

    private final UserRepository userRepo;
    private final RestaurantRepository restaurantRepo;
    private final DishRepository dishRepo;
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
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Restaurant mit der ID " + restaurantId + " wurde nicht gefunden"));

        if (restaurant.getOwner() == null || !restaurant.getOwner().getId().equals(admin.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Du darfst nur die Speisekarte deines eigenen Restaurants bearbeiten");
        }

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
