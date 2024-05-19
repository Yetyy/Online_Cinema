package com.example.cinema.controller;

import com.example.cinema.model.Film;
import com.example.cinema.model.User;
import com.example.cinema.model.Review;
import com.example.cinema.model.Genre;
import com.example.cinema.service.FilmService;
import com.example.cinema.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilmController {
    private final FilmService filmService;
    private final ReviewService reviewService;

    @Autowired
    public FilmController(FilmService filmService, ReviewService reviewService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
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
    public String getFilmById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails user) {
        Film film = filmService.getFilmById(id);
        if (film != null) {
            String formattedGenres = film.getGenres().stream()
                    .map(Genre::getCapitalizedName)
                    .collect(Collectors.joining(", "));

            model.addAttribute("film", film);
            model.addAttribute("formattedGenres", formattedGenres);
            model.addAttribute("reviewService", reviewService);
            List<Review> reviews = reviewService.getReviewsByFilmId(id);
            model.addAttribute("reviews", reviews);

            if (user != null) {
                model.addAttribute("isUserLoggedIn", true);
                model.addAttribute("user", user);
            } else {
                model.addAttribute("isUserLoggedIn", false);
            }

            return "film-details";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/film/{id}/review")
    public String addReview(@PathVariable Long id, @RequestParam String text, @RequestParam Float rating, @AuthenticationPrincipal User user) {
        if (user != null) {
            reviewService.addReview(id, user.getId(), text, rating);
        }
        return "redirect:/film/" + id;
    }

    @PostMapping("/film/{id}/review/{reviewId}/edit")
    public String editReview(@PathVariable Long id, @PathVariable Long reviewId, @RequestParam String text, @RequestParam Float rating,@AuthenticationPrincipal User user) {
        if (user != null && reviewService.isReviewBelongsToUser(reviewId, user.getId())) {
            reviewService.updateReview(reviewId, user.getId(), text, rating);
        }
        return "redirect:/film/" + id;
    }

    @PostMapping("/film/{id}/review/{reviewId}/delete")
    public String deleteReview(@PathVariable Long id, @PathVariable Long reviewId, @AuthenticationPrincipal User user) {
        if (user != null && reviewService.isReviewBelongsToUser(reviewId, user.getId())) {
            reviewService.deleteReview(reviewId, user.getId());
        }
        return "redirect:/film/" + id;
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
