package at.htlkaindorf.backend_redogo.dto;

import lombok.Data;

@Data
public class RestaurantDto {
    private Long id;
    private String name;
    private LocationDto location;
}
