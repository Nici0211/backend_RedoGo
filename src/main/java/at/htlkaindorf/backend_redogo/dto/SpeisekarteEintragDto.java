package at.htlkaindorf.backend_redogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Ein Eintrag der Speisekarte - wird sowohl für Gerichte als auch für Menüs verwendet.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeisekarteEintragDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
