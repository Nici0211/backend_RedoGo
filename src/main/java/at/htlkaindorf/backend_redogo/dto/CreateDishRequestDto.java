package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * US09: Daten, mit denen ein Restaurantinhaber ein neues Gericht anlegt.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishRequestDto {

    @NotBlank(message = "Bitte einen Namen für das Gericht angeben")
    @Size(max = 100, message = "Der Name darf höchstens 100 Zeichen lang sein")
    private String name;

    @Size(max = 500, message = "Die Beschreibung darf höchstens 500 Zeichen lang sein")
    private String description;

    @NotNull(message = "Bitte einen Preis angeben")
    @DecimalMin(value = "0.01", message = "Der Preis muss größer als 0 sein")
    @Digits(integer = 6, fraction = 2, message = "Der Preis darf höchstens 2 Nachkommastellen haben")
    private BigDecimal price;

    /** Optional - ohne Angabe ist das Gericht sofort verfügbar. */
    private Boolean available;
}
