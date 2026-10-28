package at.htlkaindorf.backend_redogo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.exception.GlobalExceptionHandler;
import at.htlkaindorf.backend_redogo.mapper.UserMapper;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import at.htlkaindorf.backend_redogo.service.UserService;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(
        classes = {UserController.class, UserService.class, GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class UserControllerDiffblueTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private UserRepository userRepository;

    private RegisterRequestDto validRegisterDto() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setHausnummer("1");
        dto.setNachname("Bella");
        dto.setOrt("Ort");
        dto.setPasswort("Passwort123");
        dto.setPlz("8530");
        dto.setStrasse("Strasse");
        dto.setTelefon("Telefon");
        dto.setVorname("Bella");
        return dto;
    }

    /**
     * Test {@link UserController#register(RegisterRequestDto)}.
     *
     * <ul>
     *   <li>Given E-Mail existiert noch nicht.
     *   <li>Then status {@link StatusResultMatchers#isCreated()}.
     * </ul>
     */
    @Test
    @DisplayName("Test register(RegisterRequestDto); given neue E-Mail; then status isCreated()")
    @Tag("ContributionFromDiffblue")
    @MethodsUnderTest({"UserResponseDto UserController.register(RegisterRequestDto)"})
    void testRegister_givenNewEmail_thenStatusIsCreated() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("jane.doe@example.org");
        UserResponseDto userResponseDto =
                UserResponseDto.builder().id(1L).email("jane.doe@example.org").vorname("Bella").build();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(RegisterRequestDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDto);

        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(validRegisterDto());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isCreated());
        verify(userRepository).save(user);
    }

    /**
     * Test {@link UserController#register(RegisterRequestDto)}.
     *
     * <ul>
     *   <li>Given E-Mail existiert bereits.
     *   <li>Then status {@link StatusResultMatchers#isConflict()}, nichts gespeichert.
     * </ul>
     */
    @Test
    @DisplayName("Test register(RegisterRequestDto); given E-Mail existiert; then status isConflict()")
    @Tag("ContributionFromDiffblue")
    @MethodsUnderTest({"UserResponseDto UserController.register(RegisterRequestDto)"})
    void testRegister_givenExistingEmail_thenStatusIsConflict() throws Exception {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(validRegisterDto());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isConflict());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test {@link UserController#login(LoginRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Passwort is {@code Passwort}.
     *   <li>Then status {@link StatusResultMatchers#isOk()}.
     * </ul>
     *
     * <p>Method under test: {@link UserController#login(LoginRequestDto)}
     */
    @Test
    @DisplayName(
            "Test login(LoginRequestDto); given User() Passwort is 'Passwort'; then status isOk()")
    @Tag("ContributionFromDiffblue")
    @MethodsUnderTest({"UserResponseDto UserController.login(LoginRequestDto)"})
    void testLogin_givenUserPasswortIsPasswort_thenStatusIsOk() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);
        UserResponseDto userResponseDto =
                UserResponseDto.builder()
                        .email("jane.doe@example.org")
                        .hausnummer("Hausnummer")
                        .id(1L)
                        .nachname("Bella")
                        .ort("Ort")
                        .plz("Plz")
                        .role(Role.USER)
                        .strasse("Strasse")
                        .telefon("Telefon")
                        .vorname("Bella")
                        .build();

        when(userRepository.findByEmail(anyString())).thenReturn(ofResult);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPasswort("Passwort");
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(loginRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"email\":\"jane.doe@example.org\",\"hausnummer\":\"Hausnummer\",\"id\":1,\"nachname\":\"Bella\",\"ort\":\"Ort\",\"plz"
                                                + "\":\"Plz\",\"role\":\"USER\",\"strasse\":\"Strasse\",\"telefon\":\"Telefon\",\"vorname\":\"Bella\"}"));
    }

    /**
     * Test {@link UserController#login(LoginRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Passwort is {@code U.U.U}.
     *   <li>Then status {@link StatusResultMatchers#isUnauthorized()}.
     * </ul>
     *
     * <p>Method under test: {@link UserController#login(LoginRequestDto)}
     */
    @Test
    @DisplayName(
            "Test login(LoginRequestDto); given User() Passwort is 'U.U.U'; then status isUnauthorized()")
    @Tag("ContributionFromDiffblue")
    @MethodsUnderTest({"UserResponseDto UserController.login(LoginRequestDto)"})
    void testLogin_givenUserPasswortIsUUU_thenStatusIsUnauthorized() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("U.U.U");
        user.setPlz("Plz");
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);
        UserResponseDto userResponseDto =
                UserResponseDto.builder()
                        .email("jane.doe@example.org")
                        .hausnummer("Hausnummer")
                        .id(1L)
                        .nachname("Bella")
                        .ort("Ort")
                        .plz("Plz")
                        .strasse("Strasse")
                        .telefon("Telefon")
                        .vorname("Bella")
                        .build();

        when(userRepository.findByEmail(anyString())).thenReturn(ofResult);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPasswort("Passwort");
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(loginRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content().string("{\"status\":401,\"message\":\"E-Mail oder Passwort ungültig\"}"));
    }

    /**
     * Test {@link UserController#login(LoginRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findByEmail(String)} return empty.
     *   <li>Then status {@link StatusResultMatchers#isUnauthorized()}.
     * </ul>
     *
     * <p>Method under test: {@link UserController#login(LoginRequestDto)}
     */
    @Test
    @DisplayName(
            "Test login(LoginRequestDto); given UserRepository findByEmail(String) return empty; then status isUnauthorized()")
    @Tag("ContributionFromDiffblue")
    @MethodsUnderTest({"UserResponseDto UserController.login(LoginRequestDto)"})
    void testLogin_givenUserRepositoryFindByEmailReturnEmpty_thenStatusIsUnauthorized()
            throws Exception {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        UserResponseDto userResponseDto =
                UserResponseDto.builder()
                        .email("jane.doe@example.org")
                        .hausnummer("Hausnummer")
                        .id(1L)
                        .nachname("Bella")
                        .ort("Ort")
                        .plz("Plz")
                        .strasse("Strasse")
                        .telefon("Telefon")
                        .vorname("Bella")
                        .build();

        when(userRepository.findByEmail(anyString())).thenReturn(emptyResult);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPasswort("Passwort");
        String content =
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(loginRequestDto);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content().string("{\"status\":401,\"message\":\"E-Mail oder Passwort ungültig\"}"));
    }
}