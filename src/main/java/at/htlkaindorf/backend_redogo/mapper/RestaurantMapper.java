package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.beans.Restaurant;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantDto dto);
    RestaurantDto toDto(Restaurant restaurant);
    List<Restaurant> toEntity(List<RestaurantDto> dto);
    List<RestaurantDto> toDto(List<Restaurant> restaurant);

    
}
