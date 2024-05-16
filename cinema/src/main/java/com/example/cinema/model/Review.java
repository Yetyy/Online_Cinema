package com.example.cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

    @Entity
    @Table(name = "reviews")
    public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty
        private String text;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "film_id")
        private Film film;

        // getters and setters
    }

