package at.htlkaindorf.backend_redogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeisekarteDto {
    private Long restaurantId;
    private String restaurantName;
    private List<SpeisekarteEintragDto> gerichte;
    private List<SpeisekarteEintragDto> menues;
}
