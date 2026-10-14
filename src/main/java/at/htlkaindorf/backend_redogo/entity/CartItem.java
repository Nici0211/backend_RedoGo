package at.htlkaindorf.backend_redogo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Eine Position im Warenkorb: entweder ein Gericht ODER ein Menü.
 * Derselbe Artikel kommt pro Warenkorb nur einmal vor, mehrfaches Hinzufügen erhöht die Menge.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cart_id", "dish_id"}),
        @UniqueConstraint(columnNames = {"cart_id", "menu_id"})
})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ShoppingCart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @Column(nullable = false)
    private int quantity;

    public String getName() {
        return dish != null ? dish.getName() : menu.getName();
    }

    public BigDecimal getUnitPrice() {
        return dish != null ? dish.getPrice() : menu.getPrice();
    }

    public BigDecimal getLineTotal() {
        return getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
