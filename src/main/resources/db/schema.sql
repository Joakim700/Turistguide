CREATE
DATABASE IF NOT EXISTS tourist_attraction CHARACTER SET utf8mb4;
USE
tourist_attraction;


DROP TABLE IF EXISTS attraction_JOINS_details;
DROP TABLE IF EXISTS attraction_tags;
DROP TABLE IF EXISTS attractions;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS cities;

CREATE TABLE cities (
    city_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE attractions (
    attraction_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    attraction_name        VARCHAR(255) NOT NULL,
    attraction_description TEXT,
    city_id                BIGINT,
    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities (city_id) ON DELETE CASCADE
);

CREATE TABLE tags (
    tag_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE attraction_tags (
    attraction_id BIGINT,
    tag_id        BIGINT,
    PRIMARY KEY (attraction_id, tag_id),
    CONSTRAINT fk_att_join FOREIGN KEY (attraction_id) REFERENCES attractions (attraction_id) ON DELETE CASCADE,
    CONSTRAINT fk_tag_join FOREIGN KEY (tag_id) REFERENCES tags (tag_id) ON DELETE CASCADE
);

-- Joiner attraction_name / city_name / tag_name i en samlet tabel
-- !!!OBS!!! SKAL! være nederst efter alle INSERT's, ellers virker GROUP_CONCAT ikke !!!!
CREATE VIEW attraction_JOINS_details AS
SELECT a.attraction_name,
       c.city_name,
       GROUP_CONCAT(t.tag_name, ', ') AS all_tags
FROM attractions a
         JOIN cities c ON a.city_id = c.city_id
         JOIN attraction_tags at ON a.attraction_id = at.attraction_id
JOIN tags t ON t.tag_id = at.tag_id
GROUP BY a.attraction_id, c.city_name, a.attraction_name;