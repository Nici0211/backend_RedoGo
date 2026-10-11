package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.SpeisekarteDto;
import at.htlkaindorf.backend_redogo.service.SpeisekarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class SpeisekarteController {

    private final SpeisekarteService service;

    @GetMapping("/{id}/speisekarte")
    public SpeisekarteDto getSpeisekarte(@PathVariable Long id) {
        return service.getSpeisekarte(id);
    }
}
