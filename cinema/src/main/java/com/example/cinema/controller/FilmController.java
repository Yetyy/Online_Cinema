package com.example.cinema.controller;

import com.example.cinema.model.Film;
import com.example.cinema.model.User;
import com.example.cinema.model.Review;
import com.example.cinema.model.Genre;
import com.example.cinema.service.FilmService;
import com.example.cinema.service.ReviewService;
import com.example.cinema.service.UserService;
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
    private final UserService userService;

    @Autowired
    public FilmController(FilmService filmService, ReviewService reviewService,UserService userService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails user) {
        List<Film> popularFilms = filmService.getPopularFilms();
        model.addAttribute("popularFilms", popularFilms);
        model.addAttribute("isUserLoggedIn", user != null);
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
                User loggedInUser = userService.findByUsername(user.getUsername());
                model.addAttribute("user", loggedInUser);
                model.addAttribute("isUserLoggedIn", true);
            } else {
                model.addAttribute("isUserLoggedIn", false);
            }

            return "film-details";
        } else {
            return "error/404";
        }
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "4") int size,
                         Model model, @AuthenticationPrincipal UserDetails user) {
        List<Film> searchResults = filmService.searchFilms(query, page, size);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("isUserLoggedIn", user != null);
        return "search-results";
    }


    @PostMapping("/film/{id}/review")
    public String addReview(@PathVariable Long id, @RequestParam String text, @RequestParam Float rating, @RequestParam String filmName, @AuthenticationPrincipal User user) {
        if (user != null) {
            reviewService.addReview(id, user.getId(), text, rating, filmName);
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


}
