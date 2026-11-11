package at.htlkaindorf.backend_redogo.dto;

public record LocationDto(
        Long postalCode,
        String streetName,
        String description
) {}
