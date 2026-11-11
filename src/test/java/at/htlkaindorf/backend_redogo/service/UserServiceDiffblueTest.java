package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.entity.Role;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.exception.InvalidCredentialsException;
import at.htlkaindorf.backend_redogo.exception.UserAlreadyExistsException;
import at.htlkaindorf.backend_redogo.mapper.UserMapper;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class UserServiceDiffblueTest {
    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Test {@link UserService#register(RegisterRequestDto)}.
     *
     * <p>Method under test: {@link UserService#register(RegisterRequestDto)}
     */
    @Test
    @DisplayName("Test register(RegisterRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.register(RegisterRequestDto)"})
    void testRegister() {
        // Arrange
        when(userRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setHausnummer("Hausnummer");
        dto.setNachname("Bella");
        dto.setOrt("Ort");
        dto.setPasswort("Passwort");
        dto.setPlz("99999");
        dto.setStrasse("Strasse");
        dto.setTelefon("Telefon");
        dto.setVorname("Bella");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
        verify(userRepository).existsByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#register(RegisterRequestDto)}.
     *
     * <p>Method under test: {@link UserService#register(RegisterRequestDto)}
     */
    @Test
    @DisplayName("Test register(RegisterRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.register(RegisterRequestDto)"})
    void testRegister2() {
        // Arrange
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(userMapper.toEntity(Mockito.<RegisterRequestDto>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setHausnummer("Hausnummer");
        dto.setNachname("Bella");
        dto.setOrt("Ort");
        dto.setPasswort("Passwort");
        dto.setPlz("99999");
        dto.setStrasse("Strasse");
        dto.setTelefon("Telefon");
        dto.setVorname("Bella");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
        verify(userMapper).toEntity(isA(RegisterRequestDto.class));
        verify(userRepository).existsByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#register(RegisterRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Email is {@code jane.doe@example.org}.
     *   <li>Then return Nachname is {@code Bella}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#register(RegisterRequestDto)}
     */
    @Test
    @DisplayName(
            "Test register(RegisterRequestDto); given User() Email is 'jane.doe@example.org'; then return Nachname is 'Bella'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.register(RegisterRequestDto)"})
    void testRegister_givenUserEmailIsJaneDoeExampleOrg_thenReturnNachnameIsBella() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setHausnummer("Hausnummer");
        user2.setId(1L);
        user2.setNachname("Bella");
        user2.setOrt("Ort");
        user2.setPasswort("Passwort");
        user2.setPlz("Plz");
        user2.setRole(Role.USER);
        user2.setStrasse("Strasse");
        user2.setTelefon("Telefon");
        user2.setVorname("Bella");
        when(userMapper.toDto(Mockito.<User>any()))
                .thenReturn(
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
                                .build());
        when(userMapper.toEntity(Mockito.<RegisterRequestDto>any())).thenReturn(user2);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setHausnummer("Hausnummer");
        dto.setNachname("Bella");
        dto.setOrt("Ort");
        dto.setPasswort("Passwort");
        dto.setPlz("99999");
        dto.setStrasse("Strasse");
        dto.setTelefon("Telefon");
        dto.setVorname("Bella");

        // Act
        UserResponseDto actualRegisterResult = userService.register(dto);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userMapper).toEntity(isA(RegisterRequestDto.class));
        verify(userRepository).existsByEmail("jane.doe@example.org");
        verify(userRepository).save(isA(User.class));
        assertEquals("Bella", actualRegisterResult.getNachname());
        assertEquals("Bella", actualRegisterResult.getVorname());
        assertEquals("Hausnummer", actualRegisterResult.getHausnummer());
        assertEquals("Ort", actualRegisterResult.getOrt());
        assertEquals("Plz", actualRegisterResult.getPlz());
        assertEquals("Strasse", actualRegisterResult.getStrasse());
        assertEquals("Telefon", actualRegisterResult.getTelefon());
        assertEquals("jane.doe@example.org", actualRegisterResult.getEmail());
        assertEquals(1L, actualRegisterResult.getId().longValue());
        assertEquals(Role.USER, actualRegisterResult.getRole());
    }

    /**
     * Test {@link UserService#register(RegisterRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#existsByEmail(String)} return {@code
     *       true}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#register(RegisterRequestDto)}
     */
    @Test
    @DisplayName(
            "Test register(RegisterRequestDto); given UserRepository existsByEmail(String) return 'true'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.register(RegisterRequestDto)"})
    void testRegister_givenUserRepositoryExistsByEmailReturnTrue() {
        // Arrange
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setHausnummer("Hausnummer");
        dto.setNachname("Bella");
        dto.setOrt("Ort");
        dto.setPasswort("Passwort");
        dto.setPlz("99999");
        dto.setStrasse("Strasse");
        dto.setTelefon("Telefon");
        dto.setVorname("Bella");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
        verify(userRepository).existsByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#login(LoginRequestDto)}.
     *
     * <p>Method under test: {@link UserService#login(LoginRequestDto)}
     */
    @Test
    @DisplayName("Test login(LoginRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.login(LoginRequestDto)"})
    void testLogin() {
        // Arrange
        when(userRepository.findByEmail(Mockito.<String>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setPasswort("Passwort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.login(dto));
        verify(userRepository).findByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#login(LoginRequestDto)}.
     *
     * <p>Method under test: {@link UserService#login(LoginRequestDto)}
     */
    @Test
    @DisplayName("Test login(LoginRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.login(LoginRequestDto)"})
    void testLogin2() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userMapper.toDto(Mockito.<User>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setPasswort("Passwort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.login(dto));
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#login(LoginRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link User#User()} Passwort is {@code Passwort}.
     *   <li>Then return Nachname is {@code Bella}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#login(LoginRequestDto)}
     */
    @Test
    @DisplayName(
            "Test login(LoginRequestDto); given User() Passwort is 'Passwort'; then return Nachname is 'Bella'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.login(LoginRequestDto)"})
    void testLogin_givenUserPasswortIsPasswort_thenReturnNachnameIsBella() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("Passwort");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userMapper.toDto(Mockito.<User>any()))
                .thenReturn(
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
                                .build());

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setPasswort("Passwort");

        // Act
        UserResponseDto actualLoginResult = userService.login(dto);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findByEmail("jane.doe@example.org");
        assertEquals("Bella", actualLoginResult.getNachname());
        assertEquals("Bella", actualLoginResult.getVorname());
        assertEquals("Hausnummer", actualLoginResult.getHausnummer());
        assertEquals("Ort", actualLoginResult.getOrt());
        assertEquals("Plz", actualLoginResult.getPlz());
        assertEquals("Strasse", actualLoginResult.getStrasse());
        assertEquals("Telefon", actualLoginResult.getTelefon());
        assertEquals("jane.doe@example.org", actualLoginResult.getEmail());
        assertEquals(1L, actualLoginResult.getId().longValue());
        assertEquals(Role.USER, actualLoginResult.getRole());
    }

    /**
     * Test {@link UserService#login(LoginRequestDto)}.
     *
     * <ul>
     *   <li>Then throw {@link InvalidCredentialsException}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#login(LoginRequestDto)}
     */
    @Test
    @DisplayName("Test login(LoginRequestDto); then throw InvalidCredentialsException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.login(LoginRequestDto)"})
    void testLogin_thenThrowInvalidCredentialsException() {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setHausnummer("Hausnummer");
        user.setId(1L);
        user.setNachname("Bella");
        user.setOrt("Ort");
        user.setPasswort("at.htlkaindorf.backend_redogo.entity.User");
        user.setPlz("Plz");
        user.setRole(Role.USER);
        user.setStrasse("Strasse");
        user.setTelefon("Telefon");
        user.setVorname("Bella");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("jane.doe@example.org");
        dto.setPasswort("Passwort");

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> userService.login(dto));
        verify(userRepository).findByEmail("jane.doe@example.org");
    }
}
