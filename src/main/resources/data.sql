INSERT INTO users (vorname, nachname, email, telefon, strasse, hausnummer, plz, ort, passwort)
VALUES
  ('Anna',  'Muster',     'anna@muster.at',    '+43 664 1234567', 'Hauptstraße',    '12', '4020', 'Linz',  'Sicher123'),
  ('Max',   'Mustermann', 'max@mustermann.at', NULL,              'Bahnhofstraße',  '5',  '1010', 'Wien',  'Passwort8'),
  ('Lisa',  'Beispiel',   'lisa@beispiel.at',  '+43 699 9876543', 'Kirchengasse',   '3',  '8010', 'Graz',  'Test1234x')
ON CONFLICT (email) DO NOTHING;

INSERT INTO location (id, postal_code, street_name, description, restaurant_id)
VALUES
    (1001, 4020, 'Landstraße 45',            'Mitten in der Linzer Einkaufsstraße, mit Abholschalter.',        NULL),
    (1002, 8010, 'Hauptplatz 1',             'Unser erster Standort im Herzen von Graz - direkt neben dem Rathaus.', NULL),
    (1003, 1060, 'Naschmarkt 12',            'Frisch und schnell direkt am Wiener Naschmarkt.',                NULL),
    (1004, 5020, 'Getreidegasse 22',         'Zentral in der Salzburger Altstadt, ideal für Touristen und Einheimische.', NULL),
    (1005, 6020, 'Maria-Theresien-Straße 18','Direkt in der Fußgängerzone mit Blick auf die Nordkette.',      NULL),
    (1006, 8230, 'Hauptplatz 5',             'Ideal für die Mittagspause der HTL Kaindorf.',                   NULL)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO restaurant (id, name, location_id)
VALUES
    (1001, 'RedoGo Linz Landstraße',              1001),
    (1002, 'RedoGo Graz Hauptplatz',              1002),
    (1003, 'RedoGo Wien Naschmarkt',              1003),
    (1004, 'RedoGo Salzburg Getreidegasse',       1004),
    (1005, 'RedoGo Innsbruck Maria-Theresien-Straße', 1005),
    (1006, 'RedoGo Hartberg Hauptplatz',          1006)
    ON CONFLICT (id) DO NOTHING;

UPDATE location l
SET restaurant_id = r.id
    FROM restaurant r
WHERE r.location_id = l.id AND l.restaurant_id IS NULL;