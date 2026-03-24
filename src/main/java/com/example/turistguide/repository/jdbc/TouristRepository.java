package com.example.turistguide.repository.jdbc;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.repository.mapper.AttractionExtractor;
import com.example.turistguide.repository.mapper.CityMapper;
import jakarta.annotation.Nullable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbc;
    private final AttractionExtractor extractor = new AttractionExtractor();
    private final CityMapper mapper = new CityMapper();


    public TouristRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String sqlQuery() {
        return "SELECT a.attraction_id, a.name, a.description, c.id as city_id, c.city_name AS city, t.tags AS tags" +
                "FROM attractions a" +
                "JOIN cities c on c.city_id = a.city_id" +
                "JOIN attraction_tags at ON a.attraction_id = at.attraction_id" +
                "JOIN tags t ON at.attraction_id = t.id" +
                "WHERE a.name = ?";
    }

    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT id, name, description, city, tags FROM attractions ORDER BY id";
        return jdbc.query(sql, extractor);
    }


    public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()

        String sql = sqlQuery();

        return jdbc.query(sql, new Object[]{name},
                new int[]{Types.VARCHAR},
                AttractionExtractor -> {
                    TouristAttraction attraction = null;
                    Set<TouristTags> tags = new HashSet<TouristTags>();

                    while (AttractionExtractor.next()) {
                        if (attraction == null) {

                            attraction = new TouristAttraction();
                            attraction.setAttractionId(AttractionExtractor.getLong("id"));
                            attraction.setName(AttractionExtractor.getString("name"));
                            attraction.setDescription(AttractionExtractor.getString("description"));
                            attraction.setCity(new City(
                                    AttractionExtractor.getLong("city_id"),
                                    AttractionExtractor.getString("city_name")));
                        }
                        String tag = AttractionExtractor.getString("tags");
                        if (tag != null) {
                            tags.add(TouristTags.valueOf(tag));
                        }
                    }
                    if (attraction != null) {
                        attraction.setTags(tags);
                    }
                    return attraction;
                }
        );
    }

    @Transactional
    // Tells the database to treat all commands as a single move. This makes it possible to redo everything and start over.
    public void updateAttraction(TouristAttraction attraction) {

        String sqlQuery = "UPDATE attractions SET name = ?, description = ?, city_id = ? WHERE attraction_id = ?";
        String sqlDeleteTags = "DELETE FROM attractions_tags WHERE attraction_name = ?";

        jdbc.update(sqlDeleteTags, attraction.getName());

        for (TouristTags tags : attraction.getTags()) {

            String sqlInsertTags = "INSERT INTO attraction_tags(attraction_id, tag_id) SELECT (SELECT attraction_id FROM attraction WHERE attraction_name = ?), tag_id FROM tags WHERE tag_name = ?";
            jdbc.update(sqlInsertTags, attraction.getName(), tags.name());
        }

        jdbc.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity());
    }


    public void addAttraction(TouristAttraction attraction) {
        String cityQuery = "SELECT * FROM cities";
        Boolean isFound = false;
        for (City c : jdbc.query(cityQuery, mapper)) {
            if (c.getName() == attraction.getCity().getName()) {
                isFound = true;
            }
        }
        if (!isFound) {
            jdbc.update("INSERT INTO cities (city_name) VALUES = ?", attraction.getCity().getName());
        }
        String sqlQuery = "INSERT INTO ATTRACTIONS (attraction_name, attraction_description, city_id) VALUES (?,?,(SELECT city_id FROM cities WHERE city_name = ?);";
        jdbc.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity().getName());
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("tags", attraction.getTags());
        String tagAmountString = attraction.getTags().stream().map(Tags -> "?").collect(Collectors.joining(", "));
        String sqlAttachTags = "INSERT INTO attraction_tags (attraction_id, tag_id) " +
                                "SELECT (SELECT attraction_id FROM attraction WHERE attraction_name = ?), tag_id FROM tags WHERE tag_name IN (" + tagAmountString + ")";
        jdbc.update(sqlAttachTags, attraction.getName(), attraction.getTags(), extractor);
    }


    public void deleteAttraction(String name) {
        String sql = "DELETE FROM attractions WHERE name = ?";
        jdbc.update(sql, name);
    }
}

