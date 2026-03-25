package com.example.turistguide.model;

import java.util.*;

public class TouristAttraction {

    Long attractionId;
    String name;
    String description;
    City city;
    Set<TouristTags> tags;
    private String cityName;

    public TouristAttraction(Long attractionId, String name, String description, City city, Set<TouristTags> tags) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
        this.cityName = cityName;
    }

    public TouristAttraction() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttractionId(Long Id){
        this.attractionId = Id;
    }

    public Long getAttractionId(){
        return attractionId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<TouristTags> getTags() {
        return tags;
    }

    public void setTags(Set<TouristTags> tags) {
        this.tags = tags;
    }

    public void addTags(TouristTags tagToAdd) {
        tags.add(tagToAdd);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TouristAttraction that = (TouristAttraction) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}