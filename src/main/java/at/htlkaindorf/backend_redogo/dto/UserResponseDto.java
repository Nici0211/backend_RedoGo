package at.htlkaindorf.backend_redogo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String vorname;
    private String nachname;
    private String email;
    private String telefon;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
}
