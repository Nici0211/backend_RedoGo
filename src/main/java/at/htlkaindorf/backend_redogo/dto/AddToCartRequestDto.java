package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Anfrage "Artikel in den Warenkorb legen". Wird die Menge weggelassen, gilt 1.
 */
@Data
public class AddToCartRequestDto {

    @NotNull(message = "Die Art des Artikels (DISH oder MENU) fehlt")
    private CartItemType type;

    @NotNull(message = "Die ID des Artikels fehlt")
    private Long itemId;

    private int quantity = 1;
}
