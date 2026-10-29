package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.CreateDishRequestDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.service.AdminDishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * US09/US11: Admin-Bereich für Restaurantinhaber.
 * Wie beim Warenkorb wird der Benutzer über die ID im Pfad angegeben (keine Token-Authentifizierung im Projekt);
 * die Rolle und der Besitz des Restaurants werden im Service geprüft.
 */
@RestController
@RequestMapping("/api/admin/{adminId}")
@RequiredArgsConstructor
public class AdminDishController {

    private final AdminDishService service;

    @GetMapping("/restaurants")
    public List<RestaurantDto> getOwnRestaurants(@PathVariable Long adminId) {
        return service.getOwnRestaurants(adminId);
    }

    @PostMapping("/restaurants/{restaurantId}/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuCardEntryDto addDish(@PathVariable Long adminId,
                                    @PathVariable Long restaurantId,
                                    @Valid @RequestBody CreateDishRequestDto request) {
        return service.addDish(adminId, restaurantId, request);
    }

    @PostMapping("/restaurants/{restaurantId}/menus")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuCardEntryDto addMenu(@PathVariable Long adminId,
                                    @PathVariable Long restaurantId,
                                    @Valid @RequestBody CreateDishRequestDto request) {
        return service.addMenu(adminId, restaurantId, request);
    }

    @DeleteMapping("/restaurants/{restaurantId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Long adminId,
                           @PathVariable Long restaurantId,
                           @PathVariable Long menuId) {
        service.deleteMenu(adminId, restaurantId, menuId);
    }

    @DeleteMapping("/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long adminId,
                           @PathVariable Long restaurantId,
                           @PathVariable Long dishId) {
        service.deleteDish(adminId, restaurantId, dishId);
    }
}
