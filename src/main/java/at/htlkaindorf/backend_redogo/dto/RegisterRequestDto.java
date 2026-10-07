package at.htlkaindorf.backend_redogo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank
    private String vorname;
    @NotBlank
    private String nachname;
    @NotBlank
    @Email
    private String email;
    private String telefon;
    @NotBlank
    private String strasse;
    @NotBlank
    private String hausnummer;
    @NotBlank
    @Pattern(regexp = "\\d{4,5}", message = "Ungültige PLZ")
    private String plz;
    @NotBlank
    private String ort;
    @NotBlank
    @Size(min = 8, message = "Mindestens 8 Zeichen")
    private String passwort;
}
