package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import at.htlkaindorf.backend_redogo.repository.DishRepository;
import at.htlkaindorf.backend_redogo.repository.MenuRepository;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {MenuCardService.class, MenuRepository.class})
@ExtendWith(SpringExtension.class)
class MenuCardServiceDiffblueTest {
    @MockitoBean
    private DishRepository dishRepository;

    @Autowired
    private MenuCardService menuCardService;

    @MockitoBean
    private MenuRepository menuRepository;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    /**
     * Test {@link MenuCardService#getMenuCard(Long)}.
     *
     * <ul>
     *   <li>Then throw {@link ResponseStatusException}.
     * </ul>
     *
     * <p>Method under test: {@link MenuCardService#getMenuCard(Long)}
     */
    @Test
    @DisplayName("Test getMenuCard(Long); then throw ResponseStatusException")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({
            "at.htlkaindorf.backend_redogo.dto.MenuCardDto MenuCardService.getMenuCard(Long)"
    })
    void testGetMenuCard_thenThrowResponseStatusException() {
        // Arrange, Act and Assert
        assertThrows(ResponseStatusException.class, () -> menuCardService.getMenuCard(1L));
    }
}
