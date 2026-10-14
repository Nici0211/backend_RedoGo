package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.AddToCartRequestDto;
import at.htlkaindorf.backend_redogo.dto.CartDto;
import at.htlkaindorf.backend_redogo.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * US1: Warenkorb eines Benutzers ansehen und Gerichte/Menüs hineinlegen.
 * Die Speisekarte selbst liefert GET /restaurant/{id}/speisekarte.
 */
@RestController
@RequestMapping("/api/users/{userId}/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService service;

    @GetMapping
    public CartDto getCart(@PathVariable Long userId) {
        return service.getCart(userId);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addItem(@PathVariable Long userId, @Valid @RequestBody AddToCartRequestDto request) {
        return service.addItem(userId, request);
    }
}
