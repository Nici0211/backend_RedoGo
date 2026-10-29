package at.htlkaindorf.backend_redogo.repository;

import at.htlkaindorf.backend_redogo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /** US11: Beim Löschen eines Gerichts müssen alle Warenkorb-Positionen dieses Gerichts entfernt werden. */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from CartItem ci where ci.dish.id = :dishId")
    void deleteByDishId(@Param("dishId") Long dishId);

    /** US11: Wie {@link #deleteByDishId}, für Menüs. */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from CartItem ci where ci.menu.id = :menuId")
    void deleteByMenuId(@Param("menuId") Long menuId);
}
