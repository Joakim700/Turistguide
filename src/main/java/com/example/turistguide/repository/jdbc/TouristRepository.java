package com.example.turistguide.repository.jdbc;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> getAllAttractions() { // Vis alle attractions
        String sql = "SELECT a.attraction_id, a.name, a.description, c.city_name AS city t.tags AS tags" +
                "FROM attractions a" +
                "JOIN cities c on c.city_id = a.city_id" +
                "JOIN attraction_tags at ON a.attraction_id = at.attraction_id" +
                "JOIN tags t ON at.attraction_id = t.id";

        return jdbcTemplate.query(sql, rs -> {
            Map<Long, TouristAttraction> map = new HashMap<>();

            while(rs.next()){
                Long id = rs.getLong("attraction_id");
                String tags = rs.getString("tags");
                TouristTags tagEnum = TouristTags.valueOf(tags);

                TouristAttraction attraction = map.get(id);
                if (attraction == null){
                    attraction.setAttractionId(rs.getLong("attraction_id"));
                    attraction.setName(rs.getString("name"));
                    attraction.setDescription(rs.getString("description"));
                    attraction.setCity(rs.getString("city"));
                    attraction.setTags(new HashSet<>());
                }
                map.put(id, attraction);

                attraction.getTags().add(tagEnum);
            }

            return new ArrayList<>(map.values());
        });
    }

    public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {//Ignores spaces and letter case for easier search
                return attraction;
            }
        }
        return null;
    }

    public void updateAttraction(TouristAttraction updateAttraction) {
        TouristAttraction existingAttraction = getAttractionByName(updateAttraction.getName());
        if (existingAttraction != null) {
            attractions.remove(existingAttraction);
            attractions.add(updateAttraction);
        }
    }

    public void saveAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
    }

    public void deleteAttraction(TouristAttraction attraction) {
        attractions.remove(attraction);
    }
}
