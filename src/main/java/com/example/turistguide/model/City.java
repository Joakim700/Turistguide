package com.example.turistguide.model;

public class City {

    private Long cityId;
    private String name;

    public City(Long cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
