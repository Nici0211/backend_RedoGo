package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateDishRequestDto(
        @NotBlank(message = "Bitte einen Namen für das Gericht angeben")
        @Size(max = 100, message = "Der Name darf höchstens 100 Zeichen lang sein")
        String name,

        @Size(max = 500, message = "Die Beschreibung darf höchstens 500 Zeichen lang sein")
        String description,

        @NotNull(message = "Bitte einen Preis angeben")
        @DecimalMin(value = "0.01", message = "Der Preis muss größer als 0 sein")
        @Digits(integer = 6, fraction = 2, message = "Der Preis darf höchstens 2 Nachkommastellen haben")
        BigDecimal price,

        Boolean available
) {}
