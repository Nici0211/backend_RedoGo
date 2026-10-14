package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.MenuCardDto;
import at.htlkaindorf.backend_redogo.dto.MenuCardEntryDto;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuCardService {

    private final RestaurantRepository restaurantRepo;
    private final DishRepository dishRepo;
    private final MenuRepository menuRepo;

    @Transactional(readOnly = true)
    public MenuCardDto getMenuCard(Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Restaurant mit der ID " + restaurantId + " wurde nicht gefunden"));

        log.info("Getting Speisekarte of Restaurant {}", restaurantId);

        List<MenuCardEntryDto> gerichte = dishRepo.findByRestaurantIdOrderByIdAsc(restaurantId).stream()
                .map(d -> new MenuCardEntryDto(d.getId(), d.getName(), d.getDescription(), d.getPrice(), d.isAvailable()))
                .toList();

        List<MenuCardEntryDto> menues = menuRepo.findByRestaurantIdOrderByIdAsc(restaurantId).stream()
                .map(m -> new MenuCardEntryDto(m.getId(), m.getName(), m.getDescription(), m.getPrice(), m.isAvailable()))
                .toList();

        return new MenuCardDto(restaurant.getId(), restaurant.getName(), gerichte, menues);
    }
}
