package at.htlkaindorf.backend_redogo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long postalCode;
    private String streetName;
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    private Restaurant restaurant;
}
