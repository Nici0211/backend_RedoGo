package at.htlkaindorf.backend_redogo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Ein Menü (Kombi-Angebot) auf der Speisekarte eines Restaurants (US28).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
