package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.*;

public record UpdateUserRequestDto(
        @NotBlank String vorname,
        @NotBlank String nachname,
        @NotBlank @Email String email,
        String telefon,
        @NotBlank String strasse,
        @NotBlank String hausnummer,
        @NotBlank @Pattern(regexp = "\\d{4,5}", message = "Ungültige PLZ") String plz,
        @NotBlank String ort
) {}
