package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantMapperTest {

    private final RestaurantMapper mapper = Mappers.getMapper(RestaurantMapper.class);

    // Test 1: list of entities is mapped 1:1 to DTOs, name is kept
    @Test
    void toDto_mapsAllRestaurants() {
        Restaurant r1 = new Restaurant();
        r1.setName("Pizzeria Napoli");       // ADAPT: setter / builder of your entity
        Restaurant r2 = new Restaurant();
        r2.setName("Sushi Zen");

        List<RestaurantDto> result = mapper.toDto(List.of(r1, r2));

        assertEquals(2, result.size());
        assertEquals("Pizzeria Napoli", result.get(0).getName());   // ADAPT: getter of your DTO
        assertEquals("Sushi Zen", result.get(1).getName());
    }

    // Test 2: empty list stays empty
    @Test
    void toDto_emptyList_returnsEmptyList() {
        assertTrue(mapper.toDto(List.of()).isEmpty());
    }
}