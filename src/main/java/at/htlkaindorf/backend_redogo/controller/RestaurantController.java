package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import at.htlkaindorf.backend_redogo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantRepository repository;
    private final RestaurantService service;
}
