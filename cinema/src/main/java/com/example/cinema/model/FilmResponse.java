package com.example.cinema.model;

import java.util.List;

public class FilmResponse {

    private List<Film> docs;

    public FilmResponse() {
    }

    public FilmResponse(List<Film> docs) {
        this.docs = docs;
    }

    public List<Film> getDocs() {
        return docs;
    }

    public void setDocs(List<Film> docs) {
        this.docs = docs;
    }
}
