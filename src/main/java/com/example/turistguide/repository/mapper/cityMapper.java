package com.example.turistguide.repository.mapper;
import com.example.turistguide.model.City;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cityMapper implements RowMapper<City> {

    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new City(
              rs.getLong("cityId"),
              rs.getString("city")
        );
    }
}
