package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.AddToCartRequestDto;
import at.htlkaindorf.backend_redogo.dto.CartDto;
import at.htlkaindorf.backend_redogo.entity.CartItem;
import at.htlkaindorf.backend_redogo.entity.Dish;
import at.htlkaindorf.backend_redogo.entity.Menu;
import at.htlkaindorf.backend_redogo.entity.ShoppingCart;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.ShoppingCartMapper;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.ShoppingCartRepository;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * US1: Als Kunde möchte ich Gerichte oder Menüs in meinen Warenkorb legen.
 * US7: Als Kunde will ich Artikel aus dem Warenkorb entfernen können.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartService {

    /** Obergrenze pro Position - verhindert Überläufe und Unsinns-Bestellungen. */
    static final int MAX_QUANTITY_PER_ITEM = 99;

    private final ShoppingCartRepository cartRepo;
    private final UserRepository userRepo;
    private final DishRepository dishRepo;
    private final MenuRepository menuRepo;
    private final ShoppingCartMapper mapper;

    @Transactional(readOnly = true)
    public CartDto getCart(Long userId) {
        findUser(userId);
        return cartRepo.findByUserId(userId)
                .map(mapper::toDto)
                .orElseGet(() -> mapper.emptyCart(userId));
    }

    @Transactional
    public CartDto addItem(Long userId, AddToCartRequestDto request) {
        User user = findUser(userId);

        int quantity = request.getQuantity();
        if (quantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Die Menge muss mindestens 1 sein");
        }
        if (quantity > MAX_QUANTITY_PER_ITEM) {
            throw tooMany();
        }

        ShoppingCart cart = cartRepo.findByUserId(userId).orElseGet(() -> new ShoppingCart(user));

        CartItem item = switch (request.getType()) {
            case DISH -> findOrCreateDishItem(cart, request.getItemId());
            case MENU -> findOrCreateMenuItem(cart, request.getItemId());
        };

        int newQuantity = item.getQuantity() + quantity;
        if (newQuantity > MAX_QUANTITY_PER_ITEM) {
            throw tooMany();
        }
        item.setQuantity(newQuantity);

        // saveAndFlush, damit neue Positionen ihre ID schon in der Antwort haben
        ShoppingCart saved = cartRepo.saveAndFlush(cart);
        log.info("User {}: {}x {} {} im Warenkorb", userId, quantity, request.getType(), request.getItemId());
        return mapper.toDto(saved);
    }

    /**
     * US7: Entfernt eine Position komplett aus dem Warenkorb. Wird die letzte Position entfernt,
     * bleibt ein leerer Warenkorb (Gesamtpreis 0) übrig.
     */
    @Transactional
    public CartDto removeItem(Long userId, Long cartItemId) {
        findUser(userId);
        ShoppingCart cart = cartRepo.findByUserId(userId).orElseThrow(() -> itemNotFound(cartItemId));
        return removeFromCart(userId, cart, cartItemId);
    }

    /**
     * US7: Setzt die Menge einer Position. Menge 0 entfernt die Position, negative Mengen
     * und Mengen über dem Limit werden abgelehnt.
     */
    @Transactional
    public CartDto updateItemQuantity(Long userId, Long cartItemId, int quantity) {
        findUser(userId);
        if (quantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Die Menge darf nicht negativ sein");
        }
        if (quantity > MAX_QUANTITY_PER_ITEM) {
            throw tooMany();
        }
        ShoppingCart cart = cartRepo.findByUserId(userId).orElseThrow(() -> itemNotFound(cartItemId));
        if (quantity == 0) {
            return removeFromCart(userId, cart, cartItemId);
        }
        CartItem item = findCartItem(cart, cartItemId);
        item.setQuantity(quantity);
        ShoppingCart saved = cartRepo.saveAndFlush(cart);
        log.info("User {}: Position {} auf Menge {} gesetzt", userId, cartItemId, quantity);
        return mapper.toDto(saved);
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    private User findUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Benutzer mit der ID " + userId + " wurde nicht gefunden"));
    }

    private CartItem findOrCreateDishItem(ShoppingCart cart, Long dishId) {
        Dish dish = dishRepo.findById(dishId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Gericht mit der ID " + dishId + " wurde nicht gefunden"));
        if (!dish.isAvailable()) {
            throw notAvailable(dish.getName());
        }
        return cart.getItems().stream()
                .filter(i -> i.getDish() != null && i.getDish().getId().equals(dishId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setDish(dish);
                    cart.getItems().add(item);
                    return item;
                });
    }

    private CartItem findOrCreateMenuItem(ShoppingCart cart, Long menuId) {
        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Menü mit der ID " + menuId + " wurde nicht gefunden"));
        if (!menu.isAvailable()) {
            throw notAvailable(menu.getName());
        }
        return cart.getItems().stream()
                .filter(i -> i.getMenu() != null && i.getMenu().getId().equals(menuId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setMenu(menu);
                    cart.getItems().add(item);
                    return item;
                });
    }

    private CartDto removeFromCart(Long userId, ShoppingCart cart, Long cartItemId) {
        CartItem item = findCartItem(cart, cartItemId);
        // orphanRemoval = true -> die Position wird beim Flush aus der DB gelöscht
        cart.getItems().remove(item);
        ShoppingCart saved = cartRepo.saveAndFlush(cart);
        log.info("User {}: Position {} aus dem Warenkorb entfernt", userId, cartItemId);
        return mapper.toDto(saved);
    }

    /** Sucht die Position im Warenkorb dieses Benutzers - fremde Positionen werden so nie gefunden. */
    private CartItem findCartItem(ShoppingCart cart, Long cartItemId) {
        return cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> itemNotFound(cartItemId));
    }

    private ResponseStatusException itemNotFound(Long cartItemId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Die Position mit der ID " + cartItemId + " ist nicht im Warenkorb");
    }

    private ResponseStatusException notAvailable(String name) {
        return new ResponseStatusException(HttpStatus.CONFLICT, "'" + name + "' ist derzeit nicht verfügbar");
    }

    private ResponseStatusException tooMany() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Pro Artikel sind maximal " + MAX_QUANTITY_PER_ITEM + " Stück möglich");
    }
}
