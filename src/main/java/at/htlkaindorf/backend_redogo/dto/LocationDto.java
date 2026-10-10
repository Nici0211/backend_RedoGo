package at.htlkaindorf.backend_redogo.dto;

import lombok.Data;

@Data
public class LocationDto {
    private Long postalCode;
    private String streetName;
    private String description;
    //private RestaurantDto restaurant;
}
