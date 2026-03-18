package com.example.turistguide.repository.jdbc;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.mapper.AttractionExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbc;
    private final AttractionExtractor extractor = new AttractionExtractor();

    public TouristRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT id, name, description, city, tags FROM attractions ORDER BY id";
        return jdbc.query(sql, extractor);
    }

 //   public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()
//        String sql = "SELECT a.attraction_id, a.name, a.description, c.city_name AS city t.tags AS tags" +
//                "FROM attractions a" +
//                "JOIN cities c on c.city_id = a.city_id" +
//                "JOIN attraction_tags at ON a.attraction_id = at.attraction_id" +
//                "JOIN tags t ON at.attraction_id = t.id"+
//                "WHERE a.name = ?";
//
//        return jdbcTemplate.queryForObject(sql)
//

//        for (TouristAttraction attraction : attractions) {
//            if (attraction.getName().equalsIgnoreCase(name)) {//Ignores spaces and letter case for easier search
//                return attraction;
//            }
//        }
//        return null;
//    }
//
//    public void updateAttraction(TouristAttraction updateAttraction) {
//        TouristAttraction existingAttraction = getAttractionByName(updateAttraction.getName());
//        if (existingAttraction != null) {
//            attractions.remove(existingAttraction);
//            attractions.add(updateAttraction);
//        }
//    }
//
//    public void saveAttraction(TouristAttraction attraction) {
//        attractions.add(attraction);
//    }
//
//    public void deleteAttraction(TouristAttraction attraction) {
//        attractions.remove(attraction);
//    }
}

