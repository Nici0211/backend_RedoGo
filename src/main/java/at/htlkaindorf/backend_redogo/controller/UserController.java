package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.LoginRequestDto;
import at.htlkaindorf.backend_redogo.dto.RegisterRequestDto;
import at.htlkaindorf.backend_redogo.dto.UpdateUserRequestDto;
import at.htlkaindorf.backend_redogo.dto.UserResponseDto;
import at.htlkaindorf.backend_redogo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody RegisterRequestDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
        return userService.login(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDto dto) {
        return userService.updateUser(id, dto);
    }
}
