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
    private final String youtubeApiKey;
    private final String youtubeApiUrl;


    public FilmApiClient(RestTemplate restTemplate,
                         @Value("${api.url}") String apiUrl,
                         @Value("${api.key}") String apiKey,
                         @Value("${youtube.api.key}") String youtubeApiKey,
                         @Value("${youtube.api.url}") String youtubeApiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.youtubeApiKey = youtubeApiKey;
        this.youtubeApiUrl = youtubeApiUrl;
    }

    public List<Film> getPopularFilms() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<FilmResponse> response = restTemplate.exchange(
                apiUrl + "/v1.4/movie?page=1&rating.kp=8-10&type=movie&votes.kp=500000-1000000&limit=3",
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

    public List<Film> searchFilms(String query, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        String url = String.format("%s/v1.4/movie/search?query=%s&page=%d&limit=%d", apiUrl, query, page, size);

        ResponseEntity<FilmResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                FilmResponse.class);

        if (response.getBody() != null) {
            return response.getBody().getDocs();
        } else {
            return new ArrayList<>();
        }
    }
    public String getFilmTrailer(String filmName) {
        String url = youtubeApiUrl + "/search?part=snippet&q=" + filmName + " trailer&type=video&key=" + youtubeApiKey;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
            if (!items.isEmpty()) {
                Map<String, Object> id = (Map<String, Object>) items.get(0).get("id");
                return (String) id.get("videoId");
            }
        }
        return null;
    }
}
