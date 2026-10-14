package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.dto.CartDto;
import at.htlkaindorf.backend_redogo.dto.CartItemDto;
import at.htlkaindorf.backend_redogo.dto.CartItemType;
import at.htlkaindorf.backend_redogo.entity.CartItem;
import at.htlkaindorf.backend_redogo.entity.ShoppingCart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class ShoppingCartMapper {

    public CartDto toDto(ShoppingCart cart) {
        List<CartItemDto> items = cart.getItems().stream().map(this::toDto).toList();
        return new CartDto(cart.getId(), cart.getUser().getId(), items, cart.getTotalPrice());
    }

    /** Leerer Warenkorb für Benutzer, die noch nichts hineingelegt haben (wird nicht gespeichert). */
    public CartDto emptyCart(Long userId) {
        return new CartDto(null, userId, List.of(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    public CartItemDto toDto(CartItem item) {
        boolean isDish = item.getDish() != null;
        return new CartItemDto(
                item.getId(),
                isDish ? CartItemType.DISH : CartItemType.MENU,
                isDish ? item.getDish().getId() : item.getMenu().getId(),
                item.getName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getLineTotal());
    }
}
