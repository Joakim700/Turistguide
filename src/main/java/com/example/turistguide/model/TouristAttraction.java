package com.example.turistguide.model;
import java.util.Objects;

public class TouristAttraction {
    String name;
    String description;

    public TouristAttraction(String name, String description) {
        this.name = name;
        this.description = description;
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