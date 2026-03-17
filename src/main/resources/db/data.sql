
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
    ('Eiffel Tower', 'An impressive tower in the heart of Paris, with a great view. Close to many other attractions.',
     (SELECT city_id FROM cities WHERE city_name = 'Paris')),

    ('Great Wall of China', 'An impressive wall spanning the northern parts of China. Spend hours walking the long paths, and taking in the impressive sights.',
     (SELECT city_id FROM cities WHERE city_name = 'Beijing')),

    ('The Little Mermaid', 'A small statue of a mermaid, calling back to the great danish writer H.C.Andersen. Its since, almost, become a symbol of the city of Copenhagen itself.',
     (SELECT city_id FROM cities WHERE city_name = 'Copenhagen')),

    ('Grand Canyon', 'A historical site, of great value to both archeologists, geologists and tourists who flock to it for the great view.',
     (SELECT city_id FROM cities WHERE city_name = 'Arizona')),

    ('Tower Of London', 'An old fort, having had funtioned as a tollhouse, a prison and a seat of governance. An important building to the city of London.',
     (SELECT city_id FROM cities WHERE city_name = 'London')),

    ('The Louvre', 'A world famous museum and art gallery housing some of the most famous artworks in the world.',
     (SELECT city_id FROM cities WHERE city_name = 'Paris'));