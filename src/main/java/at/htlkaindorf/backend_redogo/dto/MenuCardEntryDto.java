package at.htlkaindorf.backend_redogo.dto;

import java.math.BigDecimal;

public record MenuCardEntryDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        boolean available
) {}
