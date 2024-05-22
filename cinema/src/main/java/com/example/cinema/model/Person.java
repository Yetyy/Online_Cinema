package com.example.cinema.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("profession")
    private String profession;

    @JsonProperty("en_profession")
    private String enProfession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;


    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEnProfession() {
        return enProfession;
    }

    public void setEnProfession(String enProfession) {
        this.enProfession = enProfession;
    }
}
