package com.example.cinema.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class Country {
    @JsonProperty("name")
    private String name;
}
