package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.MenuCardDto;
import at.htlkaindorf.backend_redogo.service.MenuCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class MenuCardController {

    private final MenuCardService service;

    @GetMapping("/{id}/speisekarte")
    public MenuCardDto getMenuCard(@PathVariable Long id) {
        return service.getMenuCard(id);
    }
}
