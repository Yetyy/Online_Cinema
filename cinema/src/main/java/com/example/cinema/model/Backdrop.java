package com.example.cinema.model;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Backdrop {
    @JsonProperty("url")
    private String backdropUrl;

    @JsonProperty("previewUrl")
    private String backdropPreviewUrl;


    // Getters and setters

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getBackdropPreviewUrl() {
        return backdropPreviewUrl;
    }

    public void setBackdropPreviewUrl(String backdropPreviewUrl) {
        this.backdropPreviewUrl = backdropPreviewUrl;
    }
}
