package at.htlkaindorf.backend_redogo.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        Long userId,
        List<CartItemDto> items,
        BigDecimal totalPrice
) {}
