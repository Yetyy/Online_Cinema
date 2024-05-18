package com.example.cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class Genre {
    @JsonProperty("name")
    private String name;

    // Getters and setters

    public String getName() {
        return name;
    }
    public String getCapitalizedName() {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }
}
