package com.example.turistguide.repository.mapper;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class AttractionMapper implements RowMapper<TouristAttraction> {

    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {

        String tagValue = rs.getString("tags");

        TouristTags tag = (tagValue != null) ? TouristTags.valueOf(tagValue) : null;

        return new TouristAttraction(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("city"),
                Collections.singleton(tag)
        );
    }
}
