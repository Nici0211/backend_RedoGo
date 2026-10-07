package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.beans.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
