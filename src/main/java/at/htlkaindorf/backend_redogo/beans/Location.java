package at.htlkaindorf.backend_redogo.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    private Restaurant restaurant;
}
