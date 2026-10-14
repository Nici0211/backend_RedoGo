package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.dto.CartDto;
import at.htlkaindorf.backend_redogo.dto.CartItemDto;
import at.htlkaindorf.backend_redogo.dto.CartItemType;
import at.htlkaindorf.backend_redogo.entity.CartItem;
import at.htlkaindorf.backend_redogo.entity.ShoppingCart;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    default CartDto toDto(ShoppingCart cart) {
        List<CartItemDto> items = cart.getItems().stream().map(this::toDto).toList();
        return new CartDto(cart.getId(), cart.getUser().getId(), items, cart.getTotalPrice());
    }

    default CartDto emptyCart(Long userId) {
        return new CartDto(null, userId, List.of(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    default CartItemDto toDto(CartItem item) {
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
