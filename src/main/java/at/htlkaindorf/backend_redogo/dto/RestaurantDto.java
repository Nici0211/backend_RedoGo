package at.htlkaindorf.backend_redogo.dto;

import at.htlkaindorf.backend_redogo.beans.Location;
import jakarta.persistence.OneToOne;

public class RestaurantDto {
    private String name;
    private LocationDto location;
}
