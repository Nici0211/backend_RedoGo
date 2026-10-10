package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
