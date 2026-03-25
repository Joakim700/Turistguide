package com.example.turistguide.model;

public class City {

    private Long cityId;
    private String name;

    public City(Long cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }
    public City() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
