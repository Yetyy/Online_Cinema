package com.example.cinema.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private int typeNumber;
    private int year;
    private String description;
    private String shortDescription;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kp", column = @Column(name = "rating_kp"))
    })
    private Rating rating;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kp", column = @Column(name = "votes_kp"))
    })
    private Votes votes;

    private int movieLength;
    private int ageRating;

    @Embedded
    private Poster poster;

    @Embedded
    private Backdrop backdrop;

    @ElementCollection
    @CollectionTable(name = "film_genre", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "genre_name")
    private List<Genre> genres;

    @ElementCollection
    @CollectionTable(name = "film_country", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "country_name")
    private List<Country> countries;

    @JsonProperty("persons")
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> persons;

    private boolean isSeries;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Votes getVotes() {
        return votes;
    }

    public void setVotes(Votes votes) {
        this.votes = votes;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Backdrop getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(Backdrop backdrop) {
        this.backdrop = backdrop;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public boolean isSeries() {
        return isSeries;
    }

    public void setSeries(boolean series) {
        isSeries = series;
    }

    public String getDirector() {
        if (persons != null) {
            Optional<Person> director = persons.stream()
                    .filter(person -> "режиссеры".equalsIgnoreCase(person.getProfession()))
                    .findFirst();
            if (director.isPresent()) {
                return director.get().getName();
            }
        }
        return null;
    }
}
