package com.example.turistguide.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCityConverter implements Converter<String, City> {

    @Override
    public City convert(String source) {
        City city = new City();
        city.setName(source);
        return city;
    }
}