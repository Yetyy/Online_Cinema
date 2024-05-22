package com.example.cinema.model;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class Rating {
    @JsonProperty("kp")
    private float kp;

    // Getters and setters

    public float getKp() {
        return kp;
    }

    public void setKp(float kp) {
        this.kp = kp;
    }
}
