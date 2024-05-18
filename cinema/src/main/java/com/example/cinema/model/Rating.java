package com.example.cinema.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
