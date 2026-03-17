package com.example.turistguide.model;

import java.util.*;

public class TouristAttraction {
    Long attractionId;
    String name;
    String description;
    String city;
    Set<TouristTags> tags = new HashSet<>();

    public TouristAttraction(Long attractionId, String name, String description, String city, Set<TouristTags> tags) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<TouristTags> getTags() {
        return tags;
    }

    public void setTags(Set<TouristTags> tags) {
        this.tags = tags;
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