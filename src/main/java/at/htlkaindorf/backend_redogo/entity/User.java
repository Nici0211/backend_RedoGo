package at.htlkaindorf.backend_redogo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String vorname;
    @Column(nullable = false)
    private String nachname;
    @Column(nullable = false, unique = true)
    private String email;
    private String telefon;
    @Column(nullable = false)
    private String strasse;
    @Column(nullable = false)
    private String hausnummer;
    @Column(nullable = false)
    private String plz;
    @Column(nullable = false)
    private String ort;
    @Column(nullable = false)
    private String passwort;

}
