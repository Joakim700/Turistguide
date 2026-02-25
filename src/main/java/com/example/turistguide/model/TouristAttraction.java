package com.example.turistguide.model;

import com.example.turistguide.repository.TouristTags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class TouristAttraction {
    String name;
    String description;
    String city;
    Collection<TouristTags> tags = new ArrayList<>();

    public TouristAttraction(String name, String description, String city, Collection<TouristTags> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }


    public TouristAttraction(){
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Request Update
    public void setName(String name) {
        this.name = name;
    }

    // Request Update
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<TouristTags> getTags() {
        return tags;
    }

    public void setTags(Collection<TouristTags> tags) {
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