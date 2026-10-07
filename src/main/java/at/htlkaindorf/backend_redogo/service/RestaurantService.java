package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.LocationDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepo;
    private final RestaurantMapper restaurantMapper;


    public List<RestaurantDto> getAll() {
        log.info("Getting All Restaurants!");
        return restaurantMapper.toDto(restaurantRepo.findAll());
    }
}
