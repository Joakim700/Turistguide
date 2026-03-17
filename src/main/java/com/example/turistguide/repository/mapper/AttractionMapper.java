package com.example.turistguide.repository.mapper;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AttractionMapper implements RowMapper<TouristAttraction> {

    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Set<TouristTags> tagsSet = new HashSet<>();
        tagsSet.add(TouristTags.valueOf(rs.getString("tags")));

        return new TouristAttraction(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("city"),
                tagsSet
        );
    }
}
