package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequestDto dto) {
        return User.builder()
                .vorname(dto.getVorname())
                .nachname(dto.getNachname())
                .email(dto.getEmail())
                .telefon(dto.getTelefon())
                .strasse(dto.getStrasse())
                .hausnummer(dto.getHausnummer())
                .plz(dto.getPlz())
                .ort(dto.getOrt())
                .passwort(dto.getPasswort())
                .build();
    }

    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .vorname(user.getVorname())
                .nachname(user.getNachname())
                .email(user.getEmail())
                .telefon(user.getTelefon())
                .strasse(user.getStrasse())
                .hausnummer(user.getHausnummer())
                .plz(user.getPlz())
                .ort(user.getOrt())
                .build();
    }
}
