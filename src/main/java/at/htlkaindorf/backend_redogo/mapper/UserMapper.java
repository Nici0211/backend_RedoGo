package at.htlkaindorf.backend_redogo.mapper;

import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /** Rolle kommt nie vom Client: wer sich registriert, ist immer USER. */
    @Mapping(target = "role", ignore = true)
    User toEntity(RegisterRequestDto dto);
    UserResponseDto toDto(User user);
}
