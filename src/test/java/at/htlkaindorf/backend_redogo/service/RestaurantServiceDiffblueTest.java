package at.htlkaindorf.backend_redogo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.entity.Restaurant;
import at.htlkaindorf.backend_redogo.mapper.RestaurantMapper;
import at.htlkaindorf.backend_redogo.repository.RestaurantRepository;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {RestaurantService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class RestaurantServiceDiffblueTest {
    @MockitoBean
    private RestaurantMapper restaurantMapper;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Test {@link RestaurantService#getAll()}.
     *
     * <p>Method under test: {@link RestaurantService#getAll()}
     */
    @Test
    @DisplayName("Test getAll()")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getAll()"})
    void testGetAll() {
        // Arrange
        when(restaurantRepository.findAll()).thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> restaurantService.getAll());
        verify(restaurantRepository).findAll();
    }

    /**
     * Test {@link RestaurantService#getAll()}.
     *
     * <ul>
     *   <li>Given {@link RestaurantMapper} {@link RestaurantMapper#toDto(List)} return {@link
     *       ArrayList#ArrayList()}.
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getAll()}
     */
    @Test
    @DisplayName(
            "Test getAll(); given RestaurantMapper toDto(List) return ArrayList(); then return Empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getAll()"})
    void testGetAll_givenRestaurantMapperToDtoReturnArrayList_thenReturnEmpty() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any())).thenReturn(new ArrayList<>());

        // Act
        List<RestaurantDto> actualAll = restaurantService.getAll();

        // Assert
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findAll();
        assertTrue(actualAll.isEmpty());
    }

    /**
     * Test {@link RestaurantService#getAll()}.
     *
     * <ul>
     *   <li>Given {@link RestaurantMapper} {@link RestaurantMapper#toDto(List)} throw {@link
     *       ResponseStatusException#ResponseStatusException(HttpStatusCode)} with status is {@link
     *       HttpStatus#OK}.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getAll()}
     */
    @Test
    @DisplayName(
            "Test getAll(); given RestaurantMapper toDto(List) throw ResponseStatusException(HttpStatusCode) with status is OK")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getAll()"})
    void testGetAll_givenRestaurantMapperToDtoThrowResponseStatusExceptionWithStatusIsOk() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> restaurantService.getAll());
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findAll();
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName("Test getSortedBy(String, String)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy() {
        // Arrange
        when(restaurantRepository.findAll(Mockito.<Sort>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.getSortedBy("location.description", "asc"));
        verify(restaurantRepository).findAll(isA(Sort.class));
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName("Test getSortedBy(String, String)")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy2() {
        // Arrange
        when(restaurantRepository.findAll(Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.OK));

        // Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.getSortedBy("location.description", "asc"));
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findAll(isA(Sort.class));
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantMapper} {@link RestaurantMapper#toDto(List)} return {@link
     *       ArrayList#ArrayList()}.
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName(
            "Test getSortedBy(String, String); given RestaurantMapper toDto(List) return ArrayList(); then return Empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy_givenRestaurantMapperToDtoReturnArrayList_thenReturnEmpty() {
        // Arrange
        when(restaurantRepository.findAll(Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any())).thenReturn(new ArrayList<>());

        // Act
        List<RestaurantDto> actualSortedBy =
                restaurantService.getSortedBy("location.description", "asc");

        // Assert
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findAll(isA(Sort.class));
        assertTrue(actualSortedBy.isEmpty());
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>When {@code By}.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName("Test getSortedBy(String, String); given RestaurantRepository; when 'By'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy_givenRestaurantRepository_whenBy() {
        // Arrange, Act and Assert
        assertThrows(
                ResponseStatusException.class, () -> restaurantService.getSortedBy("By", "Direction"));
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <ul>
     *   <li>Given {@link RestaurantRepository}.
     *   <li>When {@code Direction}.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName("Test getSortedBy(String, String); given RestaurantRepository; when 'Direction'")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy_givenRestaurantRepository_whenDirection() {
        // Arrange, Act and Assert
        assertThrows(
                ResponseStatusException.class,
                () -> restaurantService.getSortedBy("location.description", "Direction"));
    }

    /**
     * Test {@link RestaurantService#getSortedBy(String, String)}.
     *
     * <ul>
     *   <li>When {@code desc}.
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link RestaurantService#getSortedBy(String, String)}
     */
    @Test
    @DisplayName("Test getSortedBy(String, String); when 'desc'; then return Empty")
    @Tag("ContributionFromDiffblue")
    @ManagedByDiffblue
    @MethodsUnderTest({"List RestaurantService.getSortedBy(String, String)"})
    void testGetSortedBy_whenDesc_thenReturnEmpty() {
        // Arrange
        when(restaurantRepository.findAll(Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(restaurantMapper.toDto(Mockito.<List<Restaurant>>any())).thenReturn(new ArrayList<>());

        // Act
        List<RestaurantDto> actualSortedBy =
                restaurantService.getSortedBy("location.description", "desc");

        // Assert
        verify(restaurantMapper).toDto(isA(List.class));
        verify(restaurantRepository).findAll(isA(Sort.class));
        assertTrue(actualSortedBy.isEmpty());
    }
}
