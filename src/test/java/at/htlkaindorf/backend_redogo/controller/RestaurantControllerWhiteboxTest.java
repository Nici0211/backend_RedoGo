package at.htlkaindorf.backend_redogo.controller;

import at.htlkaindorf.backend_redogo.dto.LocationDto;
import at.htlkaindorf.backend_redogo.dto.RestaurantDto;
import at.htlkaindorf.backend_redogo.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Whitebox-Tests für den RestaurantController.
 *
 * Im Gegensatz zu den Blackbox-Tests (RestaurantControllerTest) wird hier nicht über HTTP,
 * sondern direkt gegen die Klasse getestet: Der RestaurantService wird gemockt und es wird
 * geprüft, wie der Controller mit seinem Service zusammenarbeitet (Delegation, Parameter-
 * Übergabe, Rückgabeverhalten, Fehlerweitergabe) und wie er strukturell aufgebaut ist.
 */
@ExtendWith(MockitoExtension.class)
class RestaurantControllerWhiteboxTest {

    @Mock
    private RestaurantService service;

    private RestaurantController controller;

    @BeforeEach
    void setUp() {
        controller = new RestaurantController(service);
    }

    // ═════════════════════════════ A) getAll – Delegation an den Service ═════════════════════════════

    // Der Controller gibt exakt die Liste zurück, die der Service geliefert hat
    @Test
    void getAll_gibtDasErgebnisDesServicesUnveraendertZurueck() {
        List<RestaurantDto> serviceErgebnis = List.of(restaurant(1L, "RedoGo Linz Landstraße", 4020L));
        when(service.getAll()).thenReturn(serviceErgebnis);

        List<RestaurantDto> ergebnis = controller.getAll();

        assertSame(serviceErgebnis, ergebnis);
    }

    // Der Service wird für getAll genau einmal aufgerufen
    @Test
    void getAll_ruftDenServiceGenauEinmalAuf() {
        when(service.getAll()).thenReturn(List.of());

        controller.getAll();

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    // Der Controller ruft beim Laden aller Restaurants keine Sortierung auf
    @Test
    void getAll_ruftKeineSortierungAuf() {
        when(service.getAll()).thenReturn(List.of());

        controller.getAll();

        verify(service, never()).getSortedBy(any(), any());
    }

    // Eine leere Liste wird unverändert als leere Liste zurückgegeben
    @Test
    void getAll_leereListeBleibtLeereListe() {
        List<RestaurantDto> leereListe = List.of();
        when(service.getAll()).thenReturn(leereListe);

        List<RestaurantDto> ergebnis = controller.getAll();

        assertSame(leereListe, ergebnis);
        assertTrue(ergebnis.isEmpty());
    }

    // Ein null-Ergebnis des Services wird ohne Ersatz weitergereicht
    @Test
    void getAll_nullErgebnisWirdUnveraendertDurchgereicht() {
        when(service.getAll()).thenReturn(null);

        assertNull(controller.getAll());
    }

    // Der Controller hält keinen Zustand zwischen zwei Aufrufen
    @Test
    void getAll_derControllerSpeichertKeinenZustand() {
        List<RestaurantDto> ersteListe = List.of(restaurant(1L, "RedoGo Linz Landstraße", 4020L));
        List<RestaurantDto> zweiteListe = List.of(restaurant(2L, "RedoGo Graz Hauptplatz", 8010L));
        when(service.getAll()).thenReturn(ersteListe, zweiteListe);

        assertSame(ersteListe, controller.getAll());
        assertSame(zweiteListe, controller.getAll());
    }

    // ═════════════════════════════ B) getSortedBy – Delegation an den Service ═════════════════════════════

    // Sortierfeld und Richtung werden unverändert an den Service weitergereicht
    @Test
    void getSortedBy_gibtByUndDirectionUnveraendertAnDenServiceWeiter() {
        when(service.getSortedBy("location.postalCode", "desc")).thenReturn(List.of());

        controller.getSortedBy("location.postalCode", "desc");

        verify(service, times(1)).getSortedBy("location.postalCode", "desc");
        verifyNoMoreInteractions(service);
    }

    // Der Controller normalisiert die Richtung nicht – das bleibt Aufgabe des Services
    @Test
    void getSortedBy_grossgeschriebeneDirectionWirdNichtVomControllerNormalisiert() {
        when(service.getSortedBy("name", "DESC")).thenReturn(List.of());

        controller.getSortedBy("name", "DESC");

        verify(service).getSortedBy("name", "DESC");
    }

    // Der Controller gibt exakt die Liste zurück, die der Service geliefert hat
    @Test
    void getSortedBy_gibtDasErgebnisDesServicesUnveraendertZurueck() {
        List<RestaurantDto> serviceErgebnis = List.of(restaurant(1L, "RedoGo Wien Naschmarkt", 1060L));
        when(service.getSortedBy("name", "asc")).thenReturn(serviceErgebnis);

        List<RestaurantDto> ergebnis = controller.getSortedBy("name", "asc");

        assertSame(serviceErgebnis, ergebnis);
    }

    // Eine leere Liste wird unverändert als leere Liste zurückgegeben
    @Test
    void getSortedBy_leereListeBleibtLeereListe() {
        List<RestaurantDto> leereListe = List.of();
        when(service.getSortedBy("name", "asc")).thenReturn(leereListe);

        List<RestaurantDto> ergebnis = controller.getSortedBy("name", "asc");

        assertSame(leereListe, ergebnis);
        assertTrue(ergebnis.isEmpty());
    }

    // Ein leeres Sortierfeld wird ungeprüft an den Service weitergereicht
    @Test
    void getSortedBy_leeresSortierfeldWirdWeitergereicht() {
        when(service.getSortedBy("", "asc")).thenReturn(List.of());

        controller.getSortedBy("", "asc");

        verify(service).getSortedBy("", "asc");
    }

    // Ein null-Sortierfeld wird ungeprüft an den Service weitergereicht
    @Test
    void getSortedBy_nullSortierfeldWirdWeitergereicht() {
        when(service.getSortedBy(null, "asc")).thenReturn(List.of());

        controller.getSortedBy(null, "asc");

        verify(service).getSortedBy(null, "asc");
    }

    // Der Controller ruft beim Sortieren nicht zusätzlich getAll auf
    @Test
    void getSortedBy_ruftGetAllNichtZusaetzlichAuf() {
        when(service.getSortedBy("id", "asc")).thenReturn(List.of());

        controller.getSortedBy("id", "asc");

        verify(service, never()).getAll();
    }

    // ═════════════════════════════ C) Fehlerverhalten des Controllers ═════════════════════════════

    // Ein Fehler des Services wird beim Laden aller Restaurants unverändert weitergegeben
    @Test
    void getAll_serviceWirftException_ControllerGibtSieWeiter() {
        ResponseStatusException fehler =
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service nicht erreichbar");
        when(service.getAll()).thenThrow(fehler);

        ResponseStatusException geworfen =
                assertThrows(ResponseStatusException.class, () -> controller.getAll());

        assertSame(fehler, geworfen);
        assertEquals(HttpStatus.BAD_REQUEST, geworfen.getStatusCode());
    }

    // Ein Fehler des Services wird beim Sortieren unverändert weitergegeben
    @Test
    void getSortedBy_serviceWirftResponseStatusException_ControllerGibtSieWeiter() {
        ResponseStatusException fehler =
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot sort by 'gibtEsNicht'.");
        when(service.getSortedBy("gibtEsNicht", "asc")).thenThrow(fehler);

        ResponseStatusException geworfen =
                assertThrows(ResponseStatusException.class, () -> controller.getSortedBy("gibtEsNicht", "asc"));

        assertSame(fehler, geworfen);
        assertEquals(HttpStatus.BAD_REQUEST, geworfen.getStatusCode());
        assertEquals("Cannot sort by 'gibtEsNicht'.", geworfen.getReason());
    }

    // Auch unerwartete Laufzeitfehler des Services werden nicht verschluckt
    @Test
    void getSortedBy_serviceWirftUnerwarteteException_ControllerVerschlucktSieNicht() {
        IllegalStateException fehler = new IllegalStateException("Datenbank nicht erreichbar");
        when(service.getSortedBy("name", "asc")).thenThrow(fehler);

        IllegalStateException geworfen =
                assertThrows(IllegalStateException.class, () -> controller.getSortedBy("name", "asc"));

        assertSame(fehler, geworfen);
        assertEquals("Datenbank nicht erreichbar", geworfen.getMessage());
    }

    // ═════════════════════════════ D) Aufbau des Controllers ═════════════════════════════

    // Der Controller ist als RestController annotiert
    @Test
    void controllerIstAlsRestControllerAnnotiert() {
        assertNotNull(RestaurantController.class.getAnnotation(RestController.class));
    }

    // Der Controller ist unter dem Pfad /restaurant eingebunden
    @Test
    void controllerIstUnterDemPfadRestaurantEingebunden() {
        RequestMapping requestMapping = RestaurantController.class.getAnnotation(RequestMapping.class);

        assertNotNull(requestMapping);
        assertEquals("/restaurant", requestMapping.value()[0]);
    }

    // getAll ist per GET auf /restaurant/getAll gemappt
    @Test
    void getAll_istAufGetAllGemappt() throws NoSuchMethodException {
        Method getAll = RestaurantController.class.getDeclaredMethod("getAll");
        GetMapping getMapping = getAll.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertEquals("/getAll", getMapping.value()[0]);
    }

    // getSortedBy ist per GET auf /restaurant/getSortedBy gemappt
    @Test
    void getSortedBy_istAufGetSortedByGemappt() throws NoSuchMethodException {
        Method getSortedBy = RestaurantController.class.getDeclaredMethod("getSortedBy", String.class, String.class);
        GetMapping getMapping = getSortedBy.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertEquals("/getSortedBy", getMapping.value()[0]);
    }

    // Der Parameter "by" ist ein Pflichtparameter
    @Test
    void getSortedBy_byParameterIstPflicht() throws NoSuchMethodException {
        Method getSortedBy = RestaurantController.class.getDeclaredMethod("getSortedBy", String.class, String.class);
        RequestParam requestParam = AnnotatedElementUtils.findMergedAnnotation(
                getSortedBy.getParameters()[0], RequestParam.class);

        assertNotNull(requestParam);
        assertEquals("by", getSortedBy.getParameters()[0].getName());
        assertTrue(requestParam.required());
        assertEquals(ValueConstants.DEFAULT_NONE, requestParam.defaultValue());
    }

    // Der Parameter "direction" hat den Defaultwert "asc"
    @Test
    void getSortedBy_directionHatDenDefaultAsc() throws NoSuchMethodException {
        Method getSortedBy = RestaurantController.class.getDeclaredMethod("getSortedBy", String.class, String.class);
        RequestParam requestParam = AnnotatedElementUtils.findMergedAnnotation(
                getSortedBy.getParameters()[1], RequestParam.class);

        assertNotNull(requestParam);
        assertEquals("direction", getSortedBy.getParameters()[1].getName());
        assertEquals("asc", requestParam.defaultValue());
    }

    // Der Service wird über den Konstruktor injiziert
    @Test
    void serviceWirdUeberKonstruktorInjiziert() throws NoSuchMethodException {
        Constructor<RestaurantController> konstruktor =
                RestaurantController.class.getDeclaredConstructor(RestaurantService.class);

        assertNotNull(konstruktor);
        assertEquals(1, RestaurantController.class.getDeclaredConstructors().length);
    }

    // Das Service-Feld ist final, der Controller ist also zustandslos
    @Test
    void dasServiceFeldIstFinalUndVomTypRestaurantService() throws NoSuchFieldException {
        Field feld = RestaurantController.class.getDeclaredField("service");

        assertEquals(RestaurantService.class, feld.getType());
        assertTrue(Modifier.isFinal(feld.getModifiers()));
    }

    // ───────────────────────── Hilfsmethoden ─────────────────────────

    private RestaurantDto restaurant(Long id, String name, Long postalCode) {
        LocationDto location = new LocationDto();
        location.setPostalCode(postalCode);
        location.setStreetName("Teststraße 1");
        location.setDescription("Testbeschreibung");

        RestaurantDto dto = new RestaurantDto();
        dto.setId(id);
        dto.setName(name);
        dto.setLocation(location);
        return dto;
    }
}
