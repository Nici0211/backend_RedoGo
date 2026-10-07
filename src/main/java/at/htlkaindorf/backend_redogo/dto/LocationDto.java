package at.htlkaindorf.backend_redogo.dto;

import at.htlkaindorf.backend_redogo.beans.Restaurant;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class LocationDto {
    private Long postalCode;
    private String streetName;
    private String description;
    //private RestaurantDto restaurant;
}
