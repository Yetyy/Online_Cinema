package com.example.cinema.controller;
import com.example.cinema.model.User;
import com.example.cinema.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user != null) {
            model.addAttribute("isUserLoggedIn", true);
        } else {
            model.addAttribute("isUserLoggedIn", false);
        }
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!user.isConfirmPasswordValid()) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Пароли не совпадают");
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
            return "login";
        }
        // successful login logic
        return "redirect:/";
    }
    //admin not working

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (!user.hasRole("ADMIN")) {
            return "accessDenied";
        }
        // admin page logic
        return "admin";
    }
}

