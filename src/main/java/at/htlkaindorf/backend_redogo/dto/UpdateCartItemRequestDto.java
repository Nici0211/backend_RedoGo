package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * US7: Neue Menge einer Warenkorb-Position. Menge 0 entfernt die Position.
 */
@Data
public class UpdateCartItemRequestDto {

    @NotNull(message = "Die Menge fehlt")
    private Integer quantity;
}
