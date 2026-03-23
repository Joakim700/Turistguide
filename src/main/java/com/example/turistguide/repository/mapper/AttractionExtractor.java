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

    private final CityMapper cityMapper = new CityMapper();

    @Override
    public List<TouristAttraction> extractData(ResultSet rs) throws SQLException, DataAccessException {

        HashMap<Long, TouristAttraction> touristAttractionHashMap = new LinkedHashMap<>();
        int rowNum = 0;

        while (rs.next()) {
            Long id = rs.getLong("attraction_id");
            TouristAttraction attraction = touristAttractionHashMap.get(id);

            if (attraction == null) {
                City city = cityMapper.mapRow(rs, rowNum);

                attraction = new TouristAttraction(
                        id,
                        rs.getString("name"),
                        rs.getString("description"),
                        city,
                        new HashSet<>()
                );

            }

            String tagVal = rs.getString("tags");

            if (tagVal != null) {
                attraction.getTags().add(TouristTags.valueOf(tagVal));
            }

            touristAttractionHashMap.put(id, attraction);

            rowNum++;

        }
        return new ArrayList<>(touristAttractionHashMap.values());
    }
}
