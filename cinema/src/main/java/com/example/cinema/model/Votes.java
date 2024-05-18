package com.example.cinema.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Votes {
    @JsonProperty("kp")
    private int kp;

    // Getters и setters

    public int getKp() {
        return kp;
    }

    public void setKp(int kp) {
        this.kp = kp;
    }
}
