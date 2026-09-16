# Redo Go – Backend

Redo Go ist eine Bestell-App im Stil von René „Redo“ Dost, dem Gastronomen und Social-Media-Star hinter Redo XXL.

Dieses Repository enthält die REST-API hinter dem [Redo Go Frontend](../frontend_redogo). Es verwaltet Speisekarte, Bestellungen, Vouchers und die Empfehlung der Woche.

## Was das Backend macht

### Speisekarte
Stellt alle Gerichte mit Kategorien, Größen (Normal, XL, XXL), Preisen und möglichen Extras bereit.

### Bestellungen
Nimmt Bestellungen entgegen, berechnet den Endpreis inklusive Größenaufschlag, Extras, Liefergebühr und Voucher und speichert sie. Der Preis wird immer serverseitig berechnet, damit das Frontend ihn nicht manipulieren kann. Jede Bestellung hat einen Zubereitungsstatus und einen Zahlungsstatus (bezahlt oder unbezahlt), den der Inhaber ändern kann.

### Vouchers
Der Inhaber legt Vouchers mit einem eindeutigen Code an. Es gibt zwei Arten:

- **Prozent-Voucher:** senkt den Bestellpreis um einen festgelegten Prozentsatz
- **Gratis-Menü-Voucher:** ein bestimmtes Menü kostet nichts

Beim Einlösen prüft das Backend, ob der Code existiert, aktiv ist und noch gültig ist.

### Empfehlung der Woche
Pro Kalenderwoche gibt es ein empfohlenes Gericht, immer in XXL. Der Inhaber hat zwei Möglichkeiten:

- **Kalender:** Empfehlungen werden im Voraus einzelnen Kalenderwochen zugeordnet (KW1, KW2, …)
- **Zufall:** Ist für die aktuelle Woche nichts geplant, oder will der Inhaber es sich einfach machen, wählt das Backend zufällig ein Gericht aus der Speisekarte

## Datenmodell

| Entity | Beschreibung |
|---|---|
| `Product` | Gericht mit Name, Beschreibung, Basispreis, Kategorie |
| `ProductSize` | Größe eines Gerichts mit Aufpreis |
| `Extra` | Zusatz wie Sauce oder Käse mit Aufpreis |
| `Menu` | Zusammenstellung mehrerer Gerichte zu einem Preis |
| `Order` | Bestellung mit Kundendaten, Lieferart, Zeitfenster, Status, Zahlungsstatus, Gesamtpreis |
| `OrderItem` | Einzelne Position einer Bestellung mit Größe, Extras und Menge |
| `Voucher` | Code, Typ (Prozent oder Gratis-Menü), Wert bzw. Menü, Gültigkeit, aktiv ja/nein |
| `WeeklyRecommendation` | Jahr, Kalenderwoche, empfohlenes Gericht, zufällig erzeugt ja/nein |

## Endpunkte

### Kunde

| Methode | Pfad | Zweck |
|---|---|---|
| GET | `/api/products` | Speisekarte |
| GET | `/api/extras` | Verfügbare Extras |
| GET | `/api/recommendations/current` | Empfehlung der aktuellen Woche |
| POST | `/api/vouchers/validate` | Voucher-Code prüfen |
| POST | `/api/orders` | Bestellung aufgeben |
| GET | `/api/orders/{id}` | Status einer Bestellung |

### Inhaber

| Methode | Pfad | Zweck |
|---|---|---|
| GET | `/api/admin/orders?paid=true\|false` | Bestellungen, optional nach Zahlungsstatus gefiltert |
| PATCH | `/api/admin/orders/{id}/paid` | Bestellung als bezahlt oder unbezahlt markieren |
| PATCH | `/api/admin/orders/{id}/status` | Zubereitungsstatus ändern |
| GET / POST | `/api/admin/vouchers` | Vouchers auflisten und anlegen |
| PUT / DELETE | `/api/admin/vouchers/{id}` | Voucher bearbeiten oder löschen |
| GET | `/api/admin/recommendations` | Geplante Empfehlungen pro KW |
| PUT | `/api/admin/recommendations/{year}/{week}` | Empfehlung für eine KW festlegen |
| POST | `/api/admin/recommendations/random` | Zufällige Empfehlung für die aktuelle KW erzeugen |

## Technik

Java 21, Spring Boot, Spring Data JPA und PostgreSQL.

> Schulprojekt. Keine echten Bestellungen, keine offizielle Verbindung zu Redo XXL.
