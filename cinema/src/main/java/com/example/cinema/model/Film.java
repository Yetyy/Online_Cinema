package com.example.cinema.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String alternativeName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer typeNumber;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private Double kpRating;

    @Column(nullable = false)
    private Integer kpVotes;

    @Column(nullable = false)
    private Integer movieLength;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String director;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Poster and Backdrop are complex objects, so we'll create separate classes for them

    @Embedded
    private Poster poster;

    @Embedded
    private Backdrop backdrop;

    // Genres and Countries are lists, so we'll create separate classes for them as well

//    @Embedded
//    private List<Genre> genres;
//
//    @Embedded
//    private List<Country> countries;

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return movieLength;
    }

    public void setDuration(Integer movieLength) {
        this.movieLength = movieLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Backdrop getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(Backdrop backdrop) {
        this.backdrop = backdrop;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }
}
