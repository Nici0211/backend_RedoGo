package at.htlkaindorf.backend_redogo.dto;

import at.htlkaindorf.backend_redogo.entity.Role;

public record UserResponseDto(
        Long id,
        String vorname,
        String nachname,
        String email,
        String telefon,
        String strasse,
        String hausnummer,
        String plz,
        String ort,
        Role role
) {}
