-- INSERT CITIES (H2 version)
MERGE INTO cities (city_name) KEY(city_name) VALUES
    ('Paris'),
    ('Beijing'),
    ('Copenhagen'),
    ('Arizona'),
    ('London');

-- INSERT TAGS
MERGE INTO tags (tag_name) KEY(tag_name) VALUES
    ('BØRNEVENLIG'), ('GRATIS'), ('MUSEUM'), ('DYR'),
    ('KUNST'), ('NATUR'), ('SIGHTSEEING'), ('OPLEVELSE'),
    ('MINDESMÆRKE'), ('VERDENSKENDT');

-- INSERT ATTRACTIONS
INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Eiffel Tower', 'An impressive tower in the heart of Paris, with a great view. Close to many other attractions.', city_id
FROM cities WHERE city_name = 'Paris';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Great Wall of China', 'An impressive wall spanning the northern parts of China. Spend hours walking the long paths, and taking in the impressive sights.', city_id
FROM cities WHERE city_name = 'Beijing';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'The Little Mermaid', 'A small statue of a mermaid, calling back to the great danish writer H.C.Andersen. Its since, almost, become a symbol of the city of Copenhagen itself.', city_id
FROM cities WHERE city_name = 'Copenhagen';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Grand Canyon', 'A historical site, of great value to both archeologists, geologists and tourists who flock to it for the great view.', city_id
FROM cities WHERE city_name = 'Arizona';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Tower Of London', 'An old fort, having had funtioned as a tollhouse, a prison and a seat of governance. An important building to the city of London.', city_id
FROM cities WHERE city_name = 'London';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'The Louvre', 'A world famous museum and art gallery housing some of the most famous artworks in the world.', city_id
FROM cities WHERE city_name = 'Paris';

-- INSERT ATTRACTION_TAGS (unchanged, H2 supports this)
INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'Eiffel Tower' AND t.tag_name IN ('VERDENSKENDT', 'BØRNEVENLIG', 'SIGHTSEEING');

INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'Great Wall of China' AND t.tag_name IN ('BØRNEVENLIG', 'GRATIS', 'MINDESMÆRKE', 'SIGHTSEEING', 'VERDENSKENDT');

INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'The Little Mermaid' AND t.tag_name IN ('BØRNEVENLIG', 'GRATIS', 'SIGHTSEEING');

INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'Grand Canyon' AND t.tag_name IN ('GRATIS', 'SIGHTSEEING', 'BØRNEVENLIG', 'VERDENSKENDT', 'NATUR', 'OPLEVELSE');

INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'Tower Of London' AND t.tag_name IN ('DYR', 'MUSEUM', 'MINDESMÆRKE');

INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id
FROM attractions a, tags t
WHERE a.attraction_name = 'The Louvre' AND t.tag_name IN ('DYR', 'MUSEUM', 'KUNST', 'VERDENSKENDT');