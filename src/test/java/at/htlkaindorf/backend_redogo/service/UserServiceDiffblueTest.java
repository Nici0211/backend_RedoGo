package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UpdateUserRequestDto;
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
import org.springframework.web.server.ResponseStatusException;

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
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(userMapper.toEntity(Mockito.<RegisterRequestDto>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        RegisterRequestDto dto =
                new RegisterRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        "Passwort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
        verify(userMapper).toEntity(isA(RegisterRequestDto.class));
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
        UserResponseDto userResponseDto =
                new UserResponseDto(
                        1L,
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        Role.USER);
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userResponseDto);
        when(userMapper.toEntity(Mockito.<RegisterRequestDto>any())).thenReturn(user2);
        RegisterRequestDto dto =
                new RegisterRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        "Passwort");

        // Act
        UserResponseDto actualRegisterResult = userService.register(dto);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userMapper).toEntity(isA(RegisterRequestDto.class));
        verify(userRepository).existsByEmail("jane.doe@example.org");
        verify(userRepository).save(isA(User.class));
        assertSame(userResponseDto, actualRegisterResult);
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
    void testRegister3() {
        // Arrange
        when(userRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        RegisterRequestDto dto =
                new RegisterRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        "Passwort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
        verify(userRepository).existsByEmail("jane.doe@example.org");
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
        RegisterRequestDto dto =
                new RegisterRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        "Passwort");

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

        // Act and Assert
        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.login(new LoginRequestDto("jane.doe@example.org", "Passwort")));
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
        UserResponseDto userResponseDto =
                new UserResponseDto(
                        1L,
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        Role.USER);
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto actualLoginResult =
                userService.login(new LoginRequestDto("jane.doe@example.org", "Passwort"));

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findByEmail("jane.doe@example.org");
        assertSame(userResponseDto, actualLoginResult);
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
    void testLogin3() {
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

        // Act and Assert
        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.login(new LoginRequestDto("jane.doe@example.org", "Passwort")));
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findByEmail("jane.doe@example.org");
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

        // Act and Assert
        assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(new LoginRequestDto("jane.doe@example.org", "Passwort")));
        verify(userRepository).findByEmail("jane.doe@example.org");
    }

    /**
     * Test {@link UserService#getUser(Long)}.
     *
     * <p>Method under test: {@link UserService#getUser(Long)}
     */
    @Test
    @DisplayName("Test getUser(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.getUser(Long)"})
    void testGetUser() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.getUser(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#getUser(Long)}.
     *
     * <p>Method under test: {@link UserService#getUser(Long)}
     */
    @Test
    @DisplayName("Test getUser(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.getUser(Long)"})
    void testGetUser2() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userMapper.toDto(Mockito.<User>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.getUser(1L));
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#getUser(Long)}.
     *
     * <p>Method under test: {@link UserService#getUser(Long)}
     */
    @Test
    @DisplayName("Test getUser(Long)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.getUser(Long)"})
    void testGetUser3() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserResponseDto userResponseDto =
                new UserResponseDto(
                        1L,
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        Role.USER);
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto actualUser = userService.getUser(1L);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findById(1L);
        assertSame(userResponseDto, actualUser);
    }

    /**
     * Test {@link UserService#getUser(Long)}.
     *
     * <ul>
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#getUser(Long)}
     */
    @Test
    @DisplayName("Test getUser(Long); then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.getUser(Long)"})
    void testGetUser_thenThrowResponseStatusException() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userService.getUser(1L));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setEmail("john.smith@example.org");
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
        when(userRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
        verify(userRepository).existsByEmail("jane.doe@example.org");
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser2() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser3() {
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
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userMapper.toDto(Mockito.<User>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findById(1L);
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser4() {
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
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserResponseDto userResponseDto =
                new UserResponseDto(
                        1L,
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        Role.USER);
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userResponseDto);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act
        UserResponseDto actualUpdateUserResult = userService.updateUser(1L, dto);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).findById(1L);
        verify(userRepository).save(isA(User.class));
        assertSame(userResponseDto, actualUpdateUserResult);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser5() {
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
        when(userRepository.save(Mockito.<User>any()))
                .thenThrow(new UserAlreadyExistsException("jane.doe@example.org"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
        verify(userRepository).findById(1L);
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#existsByEmail(String)} return {@code
     *       false}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName(
            "Test updateUser(Long, UpdateUserRequestDto); given UserRepository existsByEmail(String) return 'false'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser_givenUserRepositoryExistsByEmailReturnFalse() {
        // Arrange
        User user = new User();
        user.setEmail("john.smith@example.org");
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
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserResponseDto userResponseDto =
                new UserResponseDto(
                        1L,
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort",
                        Role.USER);
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userResponseDto);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act
        UserResponseDto actualUpdateUserResult = userService.updateUser(1L, dto);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).existsByEmail("jane.doe@example.org");
        verify(userRepository).findById(1L);
        verify(userRepository).save(isA(User.class));
        assertSame(userResponseDto, actualUpdateUserResult);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#existsByEmail(String)} return {@code
     *       true}.
     *   <li>Then calls {@link UserRepository#existsByEmail(String)}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName(
            "Test updateUser(Long, UpdateUserRequestDto); given UserRepository existsByEmail(String) return 'true'; then calls existsByEmail(String)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser_givenUserRepositoryExistsByEmailReturnTrue_thenCallsExistsByEmail() {
        // Arrange
        User user = new User();
        user.setEmail("john.smith@example.org");
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
        when(userRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, dto));
        verify(userRepository).existsByEmail("jane.doe@example.org");
        verify(userRepository).findById(1L);
    }

    /**
     * Test {@link UserService#updateUser(Long, UpdateUserRequestDto)}.
     *
     * <ul>
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link UserService#updateUser(Long, UpdateUserRequestDto)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UpdateUserRequestDto); then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"UserResponseDto UserService.updateUser(Long, UpdateUserRequestDto)"})
    void testUpdateUser_thenThrowResponseStatusException() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        UpdateUserRequestDto dto =
                new UpdateUserRequestDto(
                        "Bella",
                        "Bella",
                        "jane.doe@example.org",
                        "Telefon",
                        "Strasse",
                        "Hausnummer",
                        "Plz",
                        "Ort");

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> userService.updateUser(1L, dto));
        verify(userRepository).findById(1L);
    }
}
