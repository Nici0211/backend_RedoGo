package at.htlkaindorf.backend_redogo.config;

import at.htlkaindorf.backend_redogo.entity.Location;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.repository.LocationRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Legt beim Start die RedoGo-Standorte an (US2), falls noch keine vorhanden sind.
 * Aktiv ueber redogo.seed.enabled=true (application.properties); in den Tests ausgeschaltet.
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "redogo.seed.enabled", havingValue = "true")
public class RestaurantDataInitializer implements ApplicationRunner {

    private final RestaurantRepository restaurantRepo;
    private final LocationRepository locationRepo;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (restaurantRepo.count() > 0) {
            return;
        }
        save("RedoGo Linz Landstraße", 4020L, "Landstraße 45",
                "Mitten in der Linzer Einkaufsstraße, mit Abholschalter.");
        save("RedoGo Graz Hauptplatz", 8010L, "Hauptplatz 1",
                "Unser erster Standort im Herzen von Graz - direkt neben dem Rathaus.");
        save("RedoGo Wien Naschmarkt", 1060L, "Naschmarkt 12",
                "Frisch und schnell direkt am Wiener Naschmarkt.");
        save("RedoGo Salzburg Getreidegasse", 5020L, "Getreidegasse 22",
                "Zentral in der Salzburger Altstadt, ideal fuer Touristen und Einheimische.");
        save("RedoGo Innsbruck Maria-Theresien-Straße", 6020L, "Maria-Theresien-Straße 18",
                "Direkt in der Fussgaengerzone mit Blick auf die Nordkette.");
        save("RedoGo Hartberg Hauptplatz", 8230L, "Hauptplatz 5",
                "Ideal fuer die Mittagspause der HTL Kaindorf.");
        log.info("{} Standorte angelegt", restaurantRepo.count());
    }

    private void save(String name, Long postalCode, String streetName, String description) {
        Location location = new Location();
        location.setPostalCode(postalCode);
        location.setStreetName(streetName);
        location.setDescription(description);
        location = locationRepo.save(location);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(location);
        restaurant = restaurantRepo.save(restaurant);

        location.setRestaurant(restaurant);
        locationRepo.save(location);
    }
}
