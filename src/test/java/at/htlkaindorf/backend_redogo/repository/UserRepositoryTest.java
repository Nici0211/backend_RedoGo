package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    private User user(String email) {
        return User.builder()
                .vorname("Anna").nachname("Muster").email(email)
                .strasse("Hauptstraße").hausnummer("5")
                .plz("4020").ort("Linz").passwort("Sicher123")
                .build();
    }

    @Test
    void findByEmail_vorhandeneEmail_gibtUserZurück() {
        userRepository.save(user("anna@muster.at"));

        assertThat(userRepository.findByEmail("anna@muster.at")).isPresent();
    }

    @Test
    void findByEmail_nichtVorhanden_gibtLeer() {
        assertThat(userRepository.findByEmail("x@x.at")).isEmpty();
    }

    @Test
    void existsByEmail_vorhandeneEmail_true() {
        userRepository.save(user("anna@muster.at"));

        assertThat(userRepository.existsByEmail("anna@muster.at")).isTrue();
    }

    @Test
    void existsByEmail_nichtVorhanden_false() {
        assertThat(userRepository.existsByEmail("x@x.at")).isFalse();
    }

    @Test
    void save_speichertKorrekt() {
        var saved = userRepository.save(user("test@test.at"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVorname()).isEqualTo("Anna");
    }
}
