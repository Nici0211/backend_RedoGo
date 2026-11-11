package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequestDto(
        @NotNull(message = "Die Menge fehlt") Integer quantity
) {}
