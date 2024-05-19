package com.example.cinema.controller;

import com.example.cinema.model.Film;
import com.example.cinema.model.Genre;
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
import java.util.stream.Collectors;

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
    public String getFilmById(@PathVariable Long id, Model model) {
        Film film = filmService.getFilmById(id);
        if (film != null) {
            // Преобразование жанров в строку
            String formattedGenres = film.getGenres().stream()
                    .map(Genre::getCapitalizedName)
                    .collect(Collectors.joining(", "));

            // Добавление фильма и форматированной строки жанров в модель
            model.addAttribute("film", film);
            model.addAttribute("formattedGenres", formattedGenres);

            return "film-details";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "4") int size,
                         Model model) {
        List<Film> searchResults = filmService.searchFilms(query, page, size);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        return "search-results";
    }
}
