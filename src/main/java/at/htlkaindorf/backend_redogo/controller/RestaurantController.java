package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.LocationDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping("/getAll")
    public List<RestaurantDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/getSortedBy")
    public List<RestaurantDto> getSortedBy(@RequestParam String by,
                                           @RequestParam(defaultValue = "asc") String direction){
        log.info("Getting all Restaurants sorted by {} {}", by, direction);
        return service.getSortedBy(by, direction);
    }
}
