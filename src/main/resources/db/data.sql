-- 1. INSERT CITIES (Removed IGNORE)
INSERT INTO cities (city_name) VALUES
                                   ('Paris'),
                                   ('Beijing'),
                                   ('Copenhagen'),
                                   ('Arizona'),
                                   ('London');

-- 2. INSERT TAGS (Removed IGNORE)
INSERT INTO tags (tag_name) VALUES
                                ('BØRNEVENLIG'), ('GRATIS'), ('MUSEUM'), ('DYR'),
                                ('KUNST'), ('NATUR'), ('SIGHTSEEING'), ('OPLEVELSE'),
                                ('MINDESMÆRKE'), ('VERDENSKENDT');

-- 3. INSERT ATTRACTIONS
-- Corrected column name to 'attraction_name' to match your schema
INSERT INTO attractions (attraction_name, attraction_description, city_id)
VALUES
    ('Eiffel Tower', 'An impressive tower in the heart of Paris, with a great view.',
     (SELECT city_id FROM cities WHERE city_name = 'Paris')),
    ('Great Wall of China', 'An impressive wall spanning the northern parts of China.',
     (SELECT city_id FROM cities WHERE city_name = 'Beijing')),
    ('The Little Mermaid', 'A small statue of a mermaid, calling back to H.C. Andersen.',
     (SELECT city_id FROM cities WHERE city_name = 'Copenhagen')),
    ('Grand Canyon', 'A historical site, of great value to both archeologists and geologists.',
     (SELECT city_id FROM cities WHERE city_name = 'Arizona')),
    ('Tower Of London', 'An old fort, having functioned as a tollhouse and a prison.',
     (SELECT city_id FROM cities WHERE city_name = 'London')),
    ('The Louvre', 'A world famous museum housing some of the most famous artworks.',
     (SELECT city_id FROM cities WHERE city_name = 'Paris'));

-- 4. INSERT ATTRACTION_TAGS
-- Corrected column name to 'attraction_name' in the subqueries
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