CREATE DATABASE IF NOT EXISTS tourist_attraction
    CHARACTER SET utf8mb4;

USE tourist_attraction;

DROP TABLE IF EXISTS cities;
CREATE TABLE IF NOT EXISTS cities (
    city_id SERIAL PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS attractions;
CREATE TABLE IF NOT EXISTS attractions (
    attraction_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    city_id INT REFERENCES cities(city_id)
);

DROP TABLE IF EXISTS tags;
CREATE TABLE IF NOT EXISTS tags (
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS attraction_tags;
CREATE TABLE IF NOT EXISTS attraction_tags (
    attraction_id INT REFERENCES attractions(attraction_id) ON DELETE CASCADE,
    tag_id INT REFERENCES tags(tag_id) ON DELETE CASCADE,
    PRIMARY KEY (attraction_id, tag_id)
);