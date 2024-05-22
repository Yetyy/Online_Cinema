package com.example.cinema.model;

import jakarta.persistence.Embeddable;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Country {
    @JsonProperty("name")
    private String name;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
