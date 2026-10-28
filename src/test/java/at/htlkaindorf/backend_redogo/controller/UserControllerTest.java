package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private static final String EXISTING_EMAIL = "vorhanden@muster.at";
    private static final String NEW_EMAIL      = "anna@muster.at";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUpDatenbank() {
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .vorname("Max")
                .nachname("Vorhanden")
                .email(EXISTING_EMAIL)
                .strasse("Teststraße")
                .hausnummer("1")
                .plz("4020")
                .ort("Linz")
                .passwort("Sicher123")
                .build());
    }

    private RegisterRequestDto validDto() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setVorname("Anna");
        dto.setNachname("Muster");
        dto.setEmail(NEW_EMAIL);
        dto.setStrasse("Hauptstraße");
        dto.setHausnummer("1");
        dto.setPlz("4020");
        dto.setOrt("Linz");
        dto.setPasswort("Sicher123");
        return dto;
    }

    // gültige Daten → 201
    @Test
    void register_gueltigeDaten_gibt201() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto())))
                .andExpect(status().isCreated());
    }

    // Antwort enthält User-Daten aber kein Passwort
    @Test
    void register_gueltigeDaten_antwortetMitUserOhnePasswort() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.vorname").value("Anna"))
                .andExpect(jsonPath("$.nachname").value("Muster"))
                .andExpect(jsonPath("$.email").value(NEW_EMAIL))
                .andExpect(jsonPath("$.passwort").doesNotExist());
    }

    // User wird in der Datenbank gespeichert
    @Test
    void register_gueltigeDaten_wirdInDatenbankGespeichert() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto())))
                .andExpect(status().isCreated());

        assertThat(userRepository.existsByEmail(NEW_EMAIL)).isTrue();
    }

    // bereits vorhandene E-Mail → 409
    @Test
    void register_emailBereitsVorhanden_gibt409() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setEmail(EXISTING_EMAIL);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    // Vorname fehlt → 400
    @Test
    void register_vornameFehlend_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setVorname("");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Nachname fehlt → 400
    @Test
    void register_nachnameFehlend_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setNachname("");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // ungültige E-Mail → 400
    @Test
    void register_emailUngueltig_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setEmail("kein-at-zeichen.de");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Passwort 7 Zeichen (GW unter) → 400
    @Test
    void register_passwort7Zeichen_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setPasswort("1234567");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Passwort 8 Zeichen (GW) → 201
    @Test
    void register_passwort8Zeichen_gibt201() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setPasswort("12345678");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    // PLZ mit Buchstaben → 400
    @Test
    void register_plzUngueltig_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setPlz("abcd");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // PLZ 3 Ziffern (GW unter) → 400
    @Test
    void register_plz3Ziffern_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setPlz("123");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // PLZ 4 Ziffern (GW) → 201
    @Test
    void register_plz4Ziffern_gibt201() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setPlz("4020");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    // Straße fehlt → 400
    @Test
    void register_strasseFehlend_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setStrasse("");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Ort fehlt → 400
    @Test
    void register_ortFehlend_gibt400() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setOrt("");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Telefon optional → 201 ohne Telefon
    @Test
    void register_telefonFehlt_gibt201() throws Exception {
        RegisterRequestDto dto = validDto();
        dto.setTelefon(null);
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    // ── Login ────────────────────────────────────────────────────────────────

    private LoginRequestDto validLoginDto() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(EXISTING_EMAIL);
        dto.setPasswort("Sicher123");
        return dto;
    }

    // korrekte Daten → 200
    @Test
    void login_gueltigeDaten_gibt200() throws Exception {
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginDto())))
                .andExpect(status().isOk());
    }

    // Antwort enthält User-Daten ohne Passwort
    @Test
    void login_gueltigeDaten_antwortetMitUserDaten() throws Exception {
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.vorname").value("Max"))
                .andExpect(jsonPath("$.email").value(EXISTING_EMAIL))
                .andExpect(jsonPath("$.passwort").doesNotExist());
    }

    // unbekannte E-Mail → 401
    @Test
    void login_emailNichtVorhanden_gibt401() throws Exception {
        LoginRequestDto dto = validLoginDto();
        dto.setEmail("gibts@nicht.at");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    // falsches Passwort → 401
    @Test
    void login_falschesPasswort_gibt401() throws Exception {
        LoginRequestDto dto = validLoginDto();
        dto.setPasswort("FalschesPasswort");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    // E-Mail fehlt → 400
    @Test
    void login_emailFehlend_gibt400() throws Exception {
        LoginRequestDto dto = validLoginDto();
        dto.setEmail("");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Passwort fehlt → 400
    @Test
    void login_passwortFehlend_gibt400() throws Exception {
        LoginRequestDto dto = validLoginDto();
        dto.setPasswort("");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // ungültiges E-Mail-Format → 400
    @Test
    void login_emailUngueltig_gibt400() throws Exception {
        LoginRequestDto dto = validLoginDto();
        dto.setEmail("kein-at-zeichen");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
