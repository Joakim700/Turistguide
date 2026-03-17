package com.example.turistguide.repository.jdbc;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.repository.mapper.AttractionMapper;
import com.example.turistguide.repository.mapper.CityMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private JdbcTemplate jdbc;
    private AttractionMapper attractionMapper = new AttractionMapper();

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbc;
    }

    public List<TouristAttraction> findAllAttractions() {
        String sql = "SELECT id, name, description, city, tags FROM attractions ORDER BY id";
        return jdbc.query(sql, attractionMapper);
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
