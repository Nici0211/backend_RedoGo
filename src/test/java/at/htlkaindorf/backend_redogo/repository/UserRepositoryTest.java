package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User anna, max, lisa;

    @BeforeEach
    void setUp() {
        anna = userRepository.save(User.builder()
                .vorname("Anna").nachname("Muster").email("anna@muster.at")
                .telefon("+43 664 1234567").strasse("Hauptstraße").hausnummer("12")
                .plz("4020").ort("Linz").passwort("Sicher123").build());

        max = userRepository.save(User.builder()
                .vorname("Max").nachname("Mustermann").email("max@mustermann.at")
                .strasse("Bahnhofstraße").hausnummer("5")
                .plz("1010").ort("Wien").passwort("Passwort8").build());

        lisa = userRepository.save(User.builder()
                .vorname("Lisa").nachname("Beispiel").email("lisa@beispiel.at")
                .telefon("+43 699 9876543").strasse("Kirchengasse").hausnummer("3")
                .plz("8010").ort("Graz").passwort("Test1234x").build());
    }

    @Test
    void dreiTestdatenVorhanden() {
        assertThat(userRepository.count()).isEqualTo(3);
    }

    @Test
    void findByEmail_anna_gefunden() {
        var result = userRepository.findByEmail("anna@muster.at");
        assertThat(result).isPresent();
        assertThat(result.get().getVorname()).isEqualTo("Anna");
        assertThat(result.get().getNachname()).isEqualTo("Muster");
    }

    @Test
    void findByEmail_max_gefunden() {
        var result = userRepository.findByEmail("max@mustermann.at");
        assertThat(result).isPresent();
        assertThat(result.get().getOrt()).isEqualTo("Wien");
        assertThat(result.get().getPlz()).isEqualTo("1010");
    }

    @Test
    void findByEmail_lisa_gefunden() {
        var result = userRepository.findByEmail("lisa@beispiel.at");
        assertThat(result).isPresent();
        assertThat(result.get().getOrt()).isEqualTo("Graz");
        assertThat(result.get().getPlz()).isEqualTo("8010");
    }

    @Test
    void findByEmail_nichtVorhanden_leer() {
        assertThat(userRepository.findByEmail("niemand@test.at")).isEmpty();
    }

    @Test
    void existsByEmail_anna_true() {
        assertThat(userRepository.existsByEmail("anna@muster.at")).isTrue();
    }

    @Test
    void existsByEmail_max_true() {
        assertThat(userRepository.existsByEmail("max@mustermann.at")).isTrue();
    }

    @Test
    void existsByEmail_lisa_true() {
        assertThat(userRepository.existsByEmail("lisa@beispiel.at")).isTrue();
    }

    @Test
    void existsByEmail_nichtVorhanden_false() {
        assertThat(userRepository.existsByEmail("niemand@test.at")).isFalse();
    }

    @Test
    void findAll_enthältAlleStädte() {
        var orte = userRepository.findAll().stream().map(User::getOrt).toList();
        assertThat(orte).containsExactlyInAnyOrder("Linz", "Wien", "Graz");
    }

    @Test
    void findAll_annaHatTelefon() {
        assertThat(anna.getTelefon()).isEqualTo("+43 664 1234567");
    }

    @Test
    void findAll_maxHatKeinTelefon() {
        assertThat(max.getTelefon()).isNull();
    }

    @Test
    void save_neuerUser_anzahlVier() {
        userRepository.save(User.builder()
                .vorname("Klaus").nachname("Test").email("klaus@salzburg.at")
                .strasse("Salzgasse").hausnummer("7").plz("5020").ort("Salzburg")
                .passwort("Klaus1234").build());
        assertThat(userRepository.count()).isEqualTo(4);
    }

    @Test
    void save_neuerUser_perEmailAbrufbar() {
        userRepository.save(User.builder()
                .vorname("Tom").nachname("Test").email("tom@test.at")
                .strasse("Testweg").hausnummer("1").plz("6020").ort("Innsbruck")
                .passwort("Tom12345").build());
        assertThat(userRepository.findByEmail("tom@test.at")).isPresent();
    }

    @Test
    void save_neuerUser_idAutomatischGesetzt() {
        var saved = userRepository.save(User.builder()
                .vorname("Eva").nachname("Test").email("eva@test.at")
                .strasse("Evagasse").hausnummer("2").plz("9020").ort("Klagenfurt")
                .passwort("Eva12345").build());
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void delete_annaEntfernen_nochZwei() {
        userRepository.delete(anna);
        assertThat(userRepository.count()).isEqualTo(2);
        assertThat(userRepository.existsByEmail("anna@muster.at")).isFalse();
    }
}
