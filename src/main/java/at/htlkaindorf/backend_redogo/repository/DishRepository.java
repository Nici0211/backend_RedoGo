package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByRestaurantIdOrderByIdAsc(Long restaurantId);

    boolean existsByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);
}
