package at.htlkaindorf.backend_redogo.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        CartItemType type,
        Long itemId,
        String name,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal
) {}
