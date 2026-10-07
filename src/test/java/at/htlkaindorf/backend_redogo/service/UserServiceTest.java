package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.exception.UserAlreadyExistsException;
import at.htlkaindorf.backend_redogo.mapper.UserMapper;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @Mock UserMapper userMapper;
    @InjectMocks UserService userService;

    private RegisterRequestDto dto() {
        var d = new RegisterRequestDto();
        d.setVorname("Anna"); d.setNachname("Muster");
        d.setEmail("anna@muster.at");
        d.setStrasse("Hauptstraße"); d.setHausnummer("5");
        d.setPlz("4020"); d.setOrt("Linz");
        d.setPasswort("Sicher123");
        return d;
    }

    @Test
    void register_gibtUserZurück() {
        var entity = User.builder().id(1L).email("anna@muster.at").build();
        var response = UserResponseDto.builder().id(1L).email("anna@muster.at").build();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userMapper.toEntity(any())).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDto(entity)).thenReturn(response);

        assertThat(userService.register(dto()).getEmail()).isEqualTo("anna@muster.at");
    }

    @Test
    void register_doppelteEmail_wirftException() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(dto()))
                .isInstanceOf(UserAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_speichertUser() {
        var entity = User.builder().build();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userMapper.toEntity(any())).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDto(any())).thenReturn(UserResponseDto.builder().build());

        userService.register(dto());

        verify(userRepository).save(entity);
    }
}
