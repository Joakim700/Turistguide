
INSERT IGNORE INTO cities (city_name) VALUES
('Paris'),
('Beijing'),
('Copenhagen'),
('Arizona'),
('London');


INSERT IGNORE INTO tags (tag_name) VALUES
('BØRNEVENLIG'), ('GRATIS'), ('MUSEUM'), ('DYR'),
('KUNST'), ('NATUR'), ('SIGHTSEEING'), ('OPLEVELSE'),
('MINDESMÆRKE'), ('VERDENSKENDT');


INSERT INTO attractions (name, description, city_id)
VALUES
    ('Eiffel Tower', 'An impressive tower in the heart of Paris...',
     (SELECT city_id FROM cities WHERE city_name = 'Paris')),

    ('Great Wall of China', 'An impressive wall spanning the northern parts of China...',
     (SELECT city_id FROM cities WHERE city_name = 'Beijing')),

    ('The Little Mermaid', 'A small statue of a mermaid...',
     (SELECT city_id FROM cities WHERE city_name = 'Copenhagen')),

    ('Grand Canyon', 'A historical site, of great value...',
     (SELECT city_id FROM cities WHERE city_name = 'Arizona')),

    ('Tower Of London', 'An old fort...',
     (SELECT city_id FROM cities WHERE city_name = 'London')),

    ('The Louvre', 'A world famous museum...',
     (SELECT city_id FROM cities WHERE city_name = 'Paris'));