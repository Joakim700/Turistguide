CREATE DATABASE IF NOT EXISTS tourist_attraction
    CHARACTER SET utf8mb4;

USE tourist_attraction;

DROP TABLE IF EXISTS attraction_tags;
DROP TABLE IF EXISTS attractions;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS cities;

CREATE TABLE cities (
    city_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE attractions (
    attraction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    city_id BIGINT,
    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE CASCADE
);


CREATE TABLE tags (
    tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE attraction_tags (
    attraction_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY (attraction_id, tag_id),
    CONSTRAINT fk_att_join FOREIGN KEY (attraction_id) REFERENCES attractions(attraction_id) ON DELETE CASCADE,
    CONSTRAINT fk_tag_join FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);