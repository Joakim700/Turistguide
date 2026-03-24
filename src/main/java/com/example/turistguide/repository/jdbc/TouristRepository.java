package com.example.turistguide.repository.jdbc;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.repository.mapper.AttractionExtractor;
import com.example.turistguide.repository.mapper.CityMapper;
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
        return "SELECT a.attraction_id, a.attraction_name, a.attraction_description, c.city_name AS cities, t.tag_name AS tags " +
                "FROM attractions a " +
                "LEFT JOIN cities c ON c.city_id = a.city_id " +
                "LEFT JOIN attraction_tags at ON a.attraction_id = at.attraction_id " +
                "LEFT JOIN tags t ON at.tag_id = t.tag_id " +
                "ORDER BY a.attraction_name";
    }

    public String sqlName() {
        return "SELECT a.attraction_id, a.attraction_name, a.attraction_description, c.city_id, c.city_name AS cities, t.tag_name AS tags " +
                "FROM attractions a " +
                "JOIN cities c ON c.city_id = a.city_id " +
                "JOIN attraction_tags at ON a.attraction_id = at.attraction_id " +
                "JOIN tags t ON at.tag_id = t.tag_id " +
                "WHERE a.attraction_name = ?";
    }

    public List<TouristAttraction> getAllAttractions() {
        return jdbc.query(sqlQuery(), extractor);
    }

    public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()

        String sql = sqlName();

        return jdbc.query(sql, new Object[]{name},
                new int[]{Types.VARCHAR},
                AttractionExtractor -> {
                    TouristAttraction attraction = null;
                    Set<TouristTags> tags = new HashSet<>();

                    while (AttractionExtractor.next()) {
                        if (attraction == null) {

                            attraction = new TouristAttraction();
                            attraction.setAttractionId(AttractionExtractor.getLong("attraction_id"));
                            attraction.setName(AttractionExtractor.getString("attraction_name"));
                            attraction.setDescription(AttractionExtractor.getString("attraction_description"));
                            attraction.setCity(new City(
                                    AttractionExtractor.getLong("city_id"),
                                    AttractionExtractor.getString("cities")));
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

    public void updateAttraction(TouristAttraction attraction) {

        String sqlQuery = "UPDATE attractions SET attraction_name = ?, attraction_description = ?, city_id = ? WHERE attraction_id = ?";
        String sqlDeleteTags = "DELETE FROM attraction_tags WHERE attraction_id = ?";

        jdbc.update(sqlDeleteTags,attraction.getAttractionId());

        String cityQuery = "SELECT * FROM cities";
        Boolean isFound = false;
        while(!isFound) {
            for (City c : jdbc.query(cityQuery, mapper)) {
                if (c.getName().equals(attraction.getCity().getName())) {
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            jdbc.update("INSERT INTO cities (city_name) VALUES (?)", attraction.getCity().getName());
        }

        for (TouristTags tags : attraction.getTags()) {

            String sqlInsertTags = "INSERT INTO attraction_tags(attraction_id, tag_id) SELECT (SELECT attraction_id FROM attractions WHERE attraction_id = ?), tag_id FROM tags WHERE tag_name = ?";
            jdbc.update(sqlInsertTags, attraction.getAttractionId(), tags.name());
        }

        jdbc.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity().getCityId(), attraction.getAttractionId());
    }

    public void addAttraction(TouristAttraction attraction) {
        String cityQuery = "SELECT * FROM cities";
        Boolean isFound = false;
        while(!isFound) {
            for (City c : jdbc.query(cityQuery, mapper)) {
                if (c.getName().equals(attraction.getCity().getName())) {
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            jdbc.update("INSERT INTO cities (city_name) VALUES (?)", attraction.getCity().getName());
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
        String sql = "DELETE FROM attractions WHERE attraction_name = ?";
        jdbc.update(sql, name);
    }
}


