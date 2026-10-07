INSERT INTO users_ (vorname, nachname, email, telefon, strasse, hausnummer, plz, ort, passwort)
VALUES
  ('Anna',  'Muster',     'anna@muster.at',    '+43 664 1234567', 'Hauptstraße',    '12', '4020', 'Linz',  'Sicher123'),
  ('Max',   'Mustermann', 'max@mustermann.at', NULL,              'Bahnhofstraße',  '5',  '1010', 'Wien',  'Passwort8'),
  ('Lisa',  'Beispiel',   'lisa@beispiel.at',  '+43 699 9876543', 'Kirchengasse',   '3',  '8010', 'Graz',  'Test1234x')
ON CONFLICT (email) DO NOTHING;
