package com.example.cinema.service;

import com.example.cinema.api.FilmApiClient;
import com.example.cinema.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final FilmApiClient filmApiClient;

    @Autowired
    public FilmService(FilmApiClient filmApiClient) {
        this.filmApiClient = filmApiClient;
    }

    public List<Film> getPopularFilms() {
        return filmApiClient.getPopularFilms();
    }

    public Film getFilmById(Long id) {
        return filmApiClient.getFilmById(id);
    }

    public List<Film> searchFilms(String query, int page, int size) {
        return filmApiClient.searchFilms(query, page, size);
    }

    public String getFilmTrailer(String filmName) {
        return filmApiClient.getFilmTrailer(filmName);
    }
}
