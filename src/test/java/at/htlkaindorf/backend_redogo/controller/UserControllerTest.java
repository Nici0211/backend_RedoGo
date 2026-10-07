package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.exception.UserAlreadyExistsException;
import at.htlkaindorf.backend_redogo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockitoBean UserService userService;

    private RegisterRequestDto validDto() {
        var dto = new RegisterRequestDto();
        dto.setVorname("Anna"); dto.setNachname("Muster");
        dto.setEmail("anna@muster.at");
        dto.setStrasse("Hauptstraße"); dto.setHausnummer("5");
        dto.setPlz("4020"); dto.setOrt("Linz");
        dto.setPasswort("Sicher123");
        return dto;
    }

    @Test
    void gültigeRegistrierung_gibt201() throws Exception {
        when(userService.register(any())).thenReturn(
                UserResponseDto.builder().id(1L).email("anna@muster.at").vorname("Anna").build());

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("anna@muster.at"));
    }

    @Test
    void fehlendeFelder_gibt400() throws Exception {
        var dto = new RegisterRequestDto(); // leer

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ungültigeEmail_gibt400() throws Exception {
        var dto = validDto();
        dto.setEmail("kein-at");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ungültigePLZ_gibt400() throws Exception {
        var dto = validDto();
        dto.setPlz("123");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void passwortZuKurz_gibt400() throws Exception {
        var dto = validDto();
        dto.setPasswort("kurz");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void doppelteEmail_gibt409() throws Exception {
        when(userService.register(any())).thenThrow(new UserAlreadyExistsException("anna@muster.at"));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto())))
                .andExpect(status().isConflict());
    }
}
