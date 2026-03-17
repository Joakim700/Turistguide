package com.example.turistguide.repository.jdbc;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {


    private final List<TouristAttraction> attractions = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    private List<TouristAttraction> findAllAttractions() { // DATABASE {Name, Description}
        String sqlAttractionPull = "SELECT name, description, attraction_id FROM attractions";
        String sqlAttractionTagsPull = "SELECT tags.tag_name AS tags, FROM attractions JOIN attraction_tags on attraction_id = attraction_tags.attraction_id JOIN tags on ";

    }

    public List<TouristAttraction> getAllAttractions() { // Vis alle attractions
        return new ArrayList<>(attractions);
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
