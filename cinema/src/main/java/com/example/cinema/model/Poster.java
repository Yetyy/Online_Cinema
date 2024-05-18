
package com.example.cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Poster {
    @JsonProperty("url")
    private String posterUrl;

    @JsonProperty("previewUrl")
    private String posterPreviewUrl;

    // Getters and setters

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterPreviewUrl() {
        return posterPreviewUrl;
    }

    public void setPosterPreviewUrl(String posterPreviewUrl) {
        this.posterPreviewUrl = posterPreviewUrl;
    }
}