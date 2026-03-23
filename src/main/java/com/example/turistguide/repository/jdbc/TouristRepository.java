package com.example.turistguide.repository.jdbc;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.repository.mapper.AttractionExtractor;
import com.example.turistguide.repository.mapper.CityMapper;
import jakarta.annotation.Nullable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbc;
    private final AttractionExtractor extractor = new AttractionExtractor();


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

    @Transactional // Tells the database to treat all commands as a single move. This makes it possible to redo everything and start over.
    public void updateAttraction(TouristAttraction attraction, int id) {

        String sqlQuery = "UPDATE attractions SET name = ?, description = ?, city_id = ? WHERE attraction_id = ?";
        String sqlDeleteTags = "DELETE FROM attractions_tags WHERE attraction_id = ?" ;

        jdbc.update(sqlDeleteTags, id);

        for (TouristTags tags : attraction.getTags()) {

            String sqlInsertTags = "INSERT INTO attraction_tags(attraction_id, tag_name) VALUES (?, ?)";
            jdbc.update(sqlInsertTags, id, tags.name());
                }

        jdbc.update(sqlQuery, attraction.getName(), attraction.getDescription(), attraction.getCity(), id);
    }




//
//    public void saveAttraction(TouristAttraction attraction) {
//        attractions.add(attraction);
//    }
//
//    public void deleteAttraction(TouristAttraction attraction) {
//        attractions.remove(attraction);
//    }
}

