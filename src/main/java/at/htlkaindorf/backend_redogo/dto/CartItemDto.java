package at.htlkaindorf.backend_redogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private CartItemType type;
    private Long itemId;
    private String name;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;
}
