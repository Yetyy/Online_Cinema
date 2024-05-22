package com.example.cinema.service;

import com.example.cinema.model.Review;
import com.example.cinema.model.User;
import com.example.cinema.repository.ReviewRepository;
import com.example.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;



    @Autowired
    public ReviewService(ReviewRepository reviewRepository,UserRepository userRepository, RestTemplate restTemplate) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "reviews")
    public void addReview(Long filmId, Long userId, String text, Float rating, String filmName) {
        Review existingReview = reviewRepository.findByFilmIdAndUserId(filmId, userId);
        if (existingReview != null) {
            // User has already left a review for this film
            throw new RuntimeException("User has already left a review for this film");
            // Or, you can return a message:
            // return "User has already left a review for this film";
        }
        Review review = new Review();
        review.setFilmId(filmId);
        review.setFilmName(filmName);
        review.setUserId(userId);
        review.setText(text);
        review.setRating(rating);
        reviewRepository.save(review);


    User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            review.setAutorName(user.getUsername());
        }

        reviewRepository.save(review);
    }

    public boolean isReviewBelongsToUser(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        return review != null && review.getUserId().equals(userId);
    }

    @CacheEvict(value = "reviews")
    public void updateReview(Long reviewId, Long userId, String text,Float rating) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null && review.getUserId().equals(userId)) {
            review.setText(text);
            review.setRating(rating);
            reviewRepository.save(review);
        }
    }

    @CacheEvict(value = "reviews")
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null && review.getUserId().equals(userId)) {
            reviewRepository.delete(review);
        }
    }
    @Cacheable(value = "reviews", key = "#filmId")
    public List<Review> getReviewsByFilmId(Long filmId) {
        return reviewRepository.findByFilmId(filmId);
    }
    public float getAverageRating(Long filmId) {
        List<Review> reviews = reviewRepository.findByFilmId(filmId);
        if (reviews.isEmpty()) {
            return (float) 0.0;
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return (float) sum / reviews.size();
    }
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

}
