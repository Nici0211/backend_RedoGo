package at.htlkaindorf.backend_redogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCardDto {
    private Long restaurantId;
    private String restaurantName;
    private List<MenuCardEntryDto> gerichte;
    private List<MenuCardEntryDto> menues;
}
