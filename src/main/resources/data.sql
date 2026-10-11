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

-- US28: Speisekarte (Gerichte und Menüs) pro Restaurant
INSERT INTO dish (id, name, description, price, restaurant_id)
VALUES
    (2001, 'Classic Cheeseburger', 'Rindfleisch-Patty, Cheddar, Salat, Tomate und Haussauce im Brioche-Bun.', 8.90, 1001),
    (2002, 'Chicken Crunch Burger', 'Knuspriges Hühnerfilet mit Krautsalat und Honig-Senf-Sauce.', 8.50, 1001),
    (2003, 'Pommes Frites', 'Knusprig frittiert, mit Meersalz.', 3.50, 1001),
    (2004, 'Coleslaw', 'Frischer Krautsalat nach Hausrezept.', 2.90, 1001),
    (2005, 'Kürbiskernöl-Burger', 'Saftiges Patty mit steirischem Kürbiskernöl, Rucola und Ziegenkäse.', 9.50, 1002),
    (2006, 'Backhendl-Salat', 'Knuspriges Backhendl auf gemischtem Blattsalat mit Kernöl-Dressing.', 10.90, 1002),
    (2007, 'Steirische Käferbohnensuppe', 'Cremige Suppe aus Käferbohnen mit Kürbiskernöl.', 5.90, 1002),
    (2008, 'Grammelknödel', 'Hausgemachte Knödel mit Grammeln, dazu Sauerkraut.', 9.20, 1002),
    (2009, 'Wiener Schnitzel', 'Klassisch vom Schwein, goldbraun gebacken, mit Petersilkartoffeln.', 12.90, 1003),
    (2010, 'Tafelspitz', 'Gekochtes Rindfleisch mit Apfelkren und Schnittlauchsauce.', 13.50, 1003),
    (2011, 'Erdäpfelsalat', 'Lauwarmer Erdäpfelsalat nach Wiener Art.', 4.20, 1003),
    (2012, 'Apfelstrudel', 'Warmer Strudel mit Vanillesauce.', 5.40, 1003),
    (2013, 'Salzburger Kasnocken', 'Hausgemachte Nocken mit Bergkäse und Röstzwiebeln.', 10.50, 1004),
    (2014, 'Pinzgauer Bratl', 'Saftiges Schweinebratl mit Semmelknödel und Krautsalat.', 12.40, 1004),
    (2015, 'Forellenfilet', 'Gebratenes Forellenfilet mit Mandelbutter und Salzkartoffeln.', 13.20, 1004),
    (2016, 'Salzburger Nockerl', 'Luftig-süße Nockerl, ideal zum Teilen.', 8.90, 1004),
    (2017, 'Tiroler Gröstl', 'Gebratene Kartoffeln mit Speck, Zwiebeln und Spiegelei.', 10.90, 1005),
    (2018, 'Kaspressknödelsuppe', 'Rindssuppe mit Kaspressknödel und frischem Schnittlauch.', 6.40, 1005),
    (2019, 'Tiroler Speckbrettl', 'Bauernspeck, Bergkäse und Bauernbrot.', 11.80, 1005),
    (2020, 'Kaiserschmarrn', 'Fluffiger Schmarrn mit Zwetschkenröster.', 8.20, 1005),
    (2021, 'Mittagsburger', 'Kleiner Burger mit Cheddar und Gurkerl - perfekt für die Pause.', 5.90, 1006),
    (2022, 'Chicken Wrap', 'Hühnerstreifen, Salat und Joghurtdressing im Weizenwrap.', 6.50, 1006),
    (2023, 'Gemüse-Laibchen', 'Knusprige Laibchen mit Kräuterdip.', 5.20, 1006),
    (2024, 'Pasta Pomodoro', 'Spaghetti mit Tomatensauce und Parmesan.', 6.80, 1006)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO menu (id, name, description, price, restaurant_id)
VALUES
    (3001, 'Burger-Menü', 'Classic Cheeseburger, Pommes Frites und ein Getränk nach Wahl.', 11.90, 1001),
    (3002, 'Crunch-Menü', 'Chicken Crunch Burger, Coleslaw und ein Getränk nach Wahl.', 11.50, 1001),
    (3003, 'Steirer-Menü', 'Käferbohnensuppe und Grammelknödel mit Sauerkraut.', 13.50, 1002),
    (3004, 'Kernöl-Menü', 'Kürbiskernöl-Burger mit Beilagensalat und Getränk.', 12.90, 1002),
    (3005, 'Wiener Menü', 'Wiener Schnitzel mit Erdäpfelsalat und Apfelstrudel.', 17.90, 1003),
    (3006, 'Naschmarkt-Menü', 'Tafelspitz mit Suppe und Getränk.', 16.50, 1003),
    (3007, 'Salzburger Menü', 'Pinzgauer Bratl mit Getränk und Salzburger Nockerl.', 19.90, 1004),
    (3008, 'Altstadt-Menü', 'Kasnocken mit Salat und Getränk.', 13.90, 1004),
    (3009, 'Tiroler Menü', 'Kaspressknödelsuppe und Tiroler Gröstl.', 14.90, 1005),
    (3010, 'Nordkette-Menü', 'Speckbrettl mit Getränk und Kaiserschmarrn.', 17.50, 1005),
    (3011, 'Schüler-Menü', 'Mittagsburger, Pommes und ein Getränk - ideal für die Mittagspause.', 7.90, 1006),
    (3012, 'Wrap-Menü', 'Chicken Wrap mit Gemüse-Laibchen und Getränk.', 9.50, 1006)
    ON CONFLICT (id) DO NOTHING;
