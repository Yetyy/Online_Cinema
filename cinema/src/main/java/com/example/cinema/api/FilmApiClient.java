package com.example.cinema.api;

import com.example.cinema.model.Film;
import com.example.cinema.model.FilmResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FilmApiClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public FilmApiClient(RestTemplate restTemplate,
                         @Value("${api.url}") String apiUrl,
                         @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public List<Film> getPopularFilms() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<FilmResponse> response = restTemplate.exchange(
                apiUrl + "/v1.4/movie?page=1&rating.kp=7-10&type=movie&votes.kp=500000-1000000&limit=3",
                HttpMethod.GET,
                request,
                FilmResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getDocs();
        } else {
            throw new RuntimeException("Failed to get popular films: " + response.getStatusCode());
        }
    }

    public Film getFilmById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<Film> response = restTemplate.exchange(
                apiUrl + "/v1.4/movie/" + id,
                HttpMethod.GET,
                request,
                Film.class);

        if (response.getBody() != null) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public List<Film> searchFilms(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<FilmResponse> response = restTemplate.exchange(
                apiUrl + "/v1.4/movie/search?query=" + query,
                HttpMethod.GET,
                request,
                FilmResponse.class);

        if (response.getBody() != null) {
            return response.getBody().getDocs();
        } else {
            return new ArrayList<>();
        }
    }
}