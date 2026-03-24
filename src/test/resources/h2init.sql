DROP TABLE IF EXISTS attraction_tags;
DROP TABLE IF EXISTS attractions;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS cities;

CREATE TABLE cities (
                        city_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        city_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE attractions (
                             attraction_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             attraction_name VARCHAR(255) NOT NULL,
                             attraction_description VARCHAR(1000),
                             city_id BIGINT,
                             CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE CASCADE
);

CREATE TABLE tags (
                      tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      tag_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE attraction_tags (
                                 attraction_id BIGINT,
                                 tag_id BIGINT,
                                 PRIMARY KEY (attraction_id, tag_id),
                                 CONSTRAINT fk_att_join FOREIGN KEY (attraction_id) REFERENCES attractions(attraction_id) ON DELETE CASCADE,
                                 CONSTRAINT fk_tag_join FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- Indsæt data
INSERT INTO cities (city_name) VALUES ('Paris'), ('Beijing'), ('Copenhagen'), ('Arizona'), ('London');

INSERT INTO tags (tag_name) VALUES ('BØRNEVENLIG'), ('GRATIS'), ('MUSEUM'), ('DYR'), ('KUNST'), ('NATUR'), ('SIGHTSEEING'), ('OPLEVELSE'), ('MINDESMÆRKE'), ('VERDENSKENDT');

-- Indsæt Attraktioner
INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Eiffel Tower', 'Description...', city_id FROM cities WHERE city_name = 'Paris';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Great Wall of China', 'Description...', city_id FROM cities WHERE city_name = 'Beijing';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'The Little Mermaid', 'Description...', city_id FROM cities WHERE city_name = 'Copenhagen';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Grand Canyon', 'Description...', city_id FROM cities WHERE city_name = 'Arizona';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'Tower Of London', 'Description...', city_id FROM cities WHERE city_name = 'London';

INSERT INTO attractions (attraction_name, attraction_description, city_id)
SELECT 'The Louvre', 'Description...', city_id FROM cities WHERE city_name = 'Paris';

-- Indsæt Tags (Eksempel for én)
INSERT INTO attraction_tags (attraction_id, tag_id)
SELECT a.attraction_id, t.tag_id FROM attractions a, tags t
WHERE a.attraction_name = 'Eiffel Tower' AND t.tag_name IN ('VERDENSKENDT', 'SIGHTSEEING');

CREATE VIEW attraction_JOINS_details AS
SELECT
    a.attraction_name,
    c.city_name,
    GROUP_CONCAT(t.tag_name SEPARATOR ', ') AS all_tags
FROM attractions a
         JOIN cities c ON a.city_id = c.city_id
         JOIN attraction_tags at ON a.attraction_id = at.attraction_id
JOIN tags t ON t.tag_id = at.tag_id
GROUP BY a.attraction_id, c.city_name, a.attraction_name;