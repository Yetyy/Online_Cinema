package com.example.cinema.service;

import com.example.cinema.api.FilmApiClient;
import com.example.cinema.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final FilmApiClient filmApiClient;

    @Autowired
    public FilmService(FilmApiClient filmApiClient) {
        this.filmApiClient = filmApiClient;
    }

    @Cacheable("popularFilms")
    public List<Film> getPopularFilms() {
        return filmApiClient.getPopularFilms();
    }

    @Cacheable(value = "films", key = "#id")
    public Film getFilmById(int id) {
        return filmApiClient.getFilmById(id);
    }

    public List<Film> searchFilms(String query, int page, int size) {
        return filmApiClient.searchFilms(query, page, size);
    }

    @Cacheable(value = "filmTrailers", key = "#filmName.concat('-').concat(#year)")
    public String getFilmTrailer(String filmName, String director, int year) {
        return filmApiClient.getFilmTrailer(filmName, director, year);
    }
}
