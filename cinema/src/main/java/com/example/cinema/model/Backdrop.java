package com.example.cinema.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class Backdrop {
    @JsonProperty("url")
    private String BackdropUrl;

    @JsonProperty("previewUrl")
    private String BackdropPreviewUrl;
}
