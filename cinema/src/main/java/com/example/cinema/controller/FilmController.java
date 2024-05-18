package com.example.cinema.controller;

import com.example.cinema.model.Film;
import com.example.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Controller
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails user) {
        List<Film> popularFilms = filmService.getPopularFilms();
        model.addAttribute("popularFilms", popularFilms);

        if (user != null) {
            model.addAttribute("isUserLoggedIn", true);
        } else {
            model.addAttribute("isUserLoggedIn", false);
        }

        return "index";
    }

    @GetMapping("/film/{id}")
    public String filmDetails(@PathVariable("id") Long id, Model model) {
        Film film = filmService.getFilmById(id);
        model.addAttribute("film", film);
        return "film-details";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Film> searchResults = filmService.searchFilms(query);
        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }
}
