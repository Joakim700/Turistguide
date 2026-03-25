package com.example.turistguide.repository.mapper;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AttractionExtractor implements ResultSetExtractor<List<TouristAttraction>> {

    @Override
    public List<TouristAttraction> extractData(ResultSet rs) throws SQLException, DataAccessException {

        int rowNum = 0;
        HashMap<Long, TouristAttraction> touristAttractionHashMap = new LinkedHashMap<>();

        while (rs.next()) {

            Long id = rs.getLong("attraction_id");
            TouristAttraction attraction = touristAttractionHashMap.get(id);

            if (attraction == null) {
                City city = new City(0L, rs.getString("cities"));

                attraction = new TouristAttraction(
                        id,
                        rs.getString("attraction_name"),
                        rs.getString("attraction_description"),
                        city,
                        new HashSet<>()
                );
                touristAttractionHashMap.put(id, attraction);
            }

            String tagsString = rs.getString("tags");

            if (tagsString != null && !tagsString.isEmpty()) {
                String[] tagArray = tagsString.split(",\\s*");

                for (String tagName : tagArray) {
                    TouristTags tag = TouristTags.valueOf(tagName.trim().toUpperCase());
                    attraction.addTags(tag);
                }
        }
            rowNum++;
        }
        return new ArrayList<>(touristAttractionHashMap.values());
    }
}


