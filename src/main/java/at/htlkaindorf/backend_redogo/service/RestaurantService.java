package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.LocationDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private static final Set<String> SORTABLE_FIELDS =
            Set.of("id", "name", "location.postalCode", "location.streetName", "location.description");

    private final RestaurantRepository restaurantRepo;
    private final RestaurantMapper restaurantMapper;


    public List<RestaurantDto> getAll() {
        log.info("Getting All Restaurants!");
        return restaurantMapper.toDto(restaurantRepo.findAll());
    }

    public List<RestaurantDto> getSortedBy(String by, String direction) {
        if (!SORTABLE_FIELDS.contains(by)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot sort by '" + by + "'. Allowed values: " + SORTABLE_FIELDS);
        }

        Sort.Direction sortDirection = switch (direction.toLowerCase(Locale.ROOT)) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "direction must be either 'asc' or 'desc', but was '" + direction + "'");
        };

        log.info("Getting All Restaurants sorted by {} {}!", by, sortDirection);
        return restaurantMapper.toDto(restaurantRepo.findAll(Sort.by(sortDirection, by)));
    }
}
