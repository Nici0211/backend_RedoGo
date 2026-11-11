package at.htlkaindorf.backend_redogo.dto;

public record RestaurantDto(
        Long id,
        String name,
        LocationDto location
) {}
