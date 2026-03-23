-- INDSÆTTER CITIES TIL DATABASE
INSERT IGNORE INTO cities (city_name) VALUES
('Paris'),
('Beijing'),
('Copenhagen'),
('Arizona'),
('London');

-- INDSÆTTER TAGS TIL DATABASE
INSERT IGNORE INTO tags (tag_name) VALUES
('BØRNEVENLIG'), ('GRATIS'), ('MUSEUM'), ('DYR'),
('KUNST'), ('NATUR'), ('SIGHTSEEING'), ('OPLEVELSE'),
('MINDESMÆRKE'), ('VERDENSKENDT');

-- INDSÆTTER ATTRACTIONS TIL DATABASE
INSERT INTO attractions (attraction_name, attraction_description, city_id)
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

-- INDSÆTTER ATTRACTION_TAGS TIL DATABASE
INSERT INTO attraction_tags (attraction_id, tag_id)

SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'Eiffel Tower'),
       tag_id FROM tags WHERE tag_name IN ('VERDENSKENDT', 'BØRNEVENLIG', 'SIGHTSEEING')
UNION ALL
SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'Great Wall of China'),
       tag_id FROM tags WHERE tag_name IN ('BØRNEVENLIG', 'GRATIS', 'MINDESMÆRKE', 'SIGHTSEEING', 'VERDENSKENDT')
UNION ALL
SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'The Little Mermaid'),
       tag_id FROM tags WHERE tag_name IN ('BØRNEVENLIG', 'GRATIS', 'SIGHTSEEING')
UNION ALL
SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'Grand Canyon'),
       tag_id FROM tags WHERE tag_name IN ('GRATIS', 'SIGHTSEEING', 'BØRNEVENLIG', 'VERDENSKENDT', 'NATUR', 'OPLEVELSE')
UNION ALL
SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'Tower Of London'),
       tag_id FROM tags WHERE tag_name IN ('DYR', 'MUSEUM', 'MINDESMÆRKE')
UNION ALL
SELECT (SELECT attraction_id FROM attractions WHERE attraction_name = 'The Louvre'),
       tag_id FROM tags WHERE tag_name IN ('DYR', 'MUSEUM', 'KUNST', 'VERDENSKENDT');