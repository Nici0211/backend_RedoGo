package at.htlkaindorf.backend_redogo.dto;

import java.util.List;

public record MenuCardDto(
        Long restaurantId,
        String restaurantName,
        List<MenuCardEntryDto> gerichte,
        List<MenuCardEntryDto> menues
) {}
