package at.htlkaindorf.backend_redogo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Ein Gericht auf der Speisekarte eines Restaurants (US28).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    /** Länge passt zur Validierung in CreateDishRequestDto (max. 500 Zeichen). */
    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    /** Nicht verfügbare Einträge werden angezeigt, können aber nicht in den Warenkorb gelegt werden (US1). */
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean available = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
