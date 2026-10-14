package at.htlkaindorf.backend_redogo.Service;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepo;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService service;

    // Test 1: service loads all restaurants and maps them to DTOs
    @Test
    void getAll_returnsMappedRestaurants() {
        List<Restaurant> entities = List.of(mock(Restaurant.class), mock(Restaurant.class));
        List<RestaurantDto> dtos = List.of(mock(RestaurantDto.class), mock(RestaurantDto.class));

        when(restaurantRepo.findAll()).thenReturn(entities);
        when(restaurantMapper.toDto(entities)).thenReturn(dtos);

        List<RestaurantDto> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals(dtos, result);
        verify(restaurantRepo).findAll();
    }

    // Test 2: no restaurants in the database -> empty list
    @Test
    void getAll_noRestaurants_returnsEmptyList() {
        when(restaurantRepo.findAll()).thenReturn(List.of());
        when(restaurantMapper.toDto(List.of())).thenReturn(List.of());

        assertTrue(service.getAll().isEmpty());
    }
}