package com.example.cinema.controller;

import com.example.cinema.model.Review;
import com.example.cinema.model.User;
import com.example.cinema.service.ReviewService;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public UserController(UserService userService,ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("isUserLoggedIn", user != null);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isUserLoggedIn", false); // Assuming a new user is not logged in
        return "register";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        List<Review> userReviews = reviewService.getReviewsByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("isUserLoggedIn", true);
        model.addAttribute("reviews", userReviews);
        return "profile";
    }


    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUserLoggedIn", false);
            return "register";
        }
        if (!user.isConfirmPasswordValid()) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Пароли не совпадают");
            model.addAttribute("isUserLoggedIn", false);
            return "register";
        }
        userService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userService.findByUsername(username);
        if (user == null || !user.isPasswordValid(password)) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("isUserLoggedIn", false);
            return "login";
        }
        // successful login logic
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal User user) {
        if (user == null || !user.hasRole("ADMIN")) {
            model.addAttribute("isUserLoggedIn", user != null);
            return "accessDenied";
        }
        model.addAttribute("isUserLoggedIn", true);
        // admin page logic
        return "admin";
    }
}
