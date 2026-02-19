package com.example.turistguide.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TouristAttraction {
    String name;
    String description;
    String city;
    List<String> tags = new ArrayList<>();

    public TouristAttraction(String name, String description, String city, List<String> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Request Update
    public String setName(String newName) {
        return this.name = newName;
    }

    // Request Update
    public String setDescription(String newDescription) {
        return this.description = newDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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