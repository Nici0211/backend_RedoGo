package at.htlkaindorf.backend_redogo.service;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UpdateUserRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.exception.InvalidCredentialsException;
import at.htlkaindorf.backend_redogo.exception.UserAlreadyExistsException;
import at.htlkaindorf.backend_redogo.entity.User;
import at.htlkaindorf.backend_redogo.mapper.UserMapper;
import at.htlkaindorf.backend_redogo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException(dto.email());
        }
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(InvalidCredentialsException::new);
        if (!user.getPasswort().equals(dto.passwort())) {
            throw new InvalidCredentialsException();
        }
        return userMapper.toDto(user);
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden"));
        return userMapper.toDto(user);
    }

    public UserResponseDto updateUser(Long id, UpdateUserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden"));
        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException(dto.email());
        }
        user.setVorname(dto.vorname());
        user.setNachname(dto.nachname());
        user.setEmail(dto.email());
        user.setTelefon(dto.telefon());
        user.setStrasse(dto.strasse());
        user.setHausnummer(dto.hausnummer());
        user.setPlz(dto.plz());
        user.setOrt(dto.ort());
        return userMapper.toDto(userRepository.save(user));
    }
}
