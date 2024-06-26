package com.example.cinema.repository;

import com.example.cinema.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFilmId(int filmId);
    List<Review> findByUserId(Long userId);
    Review findByFilmIdAndUserId(int filmId, Long userId);
}
