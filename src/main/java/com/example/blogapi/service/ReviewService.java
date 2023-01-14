package com.example.blogapi.service;

import com.example.blogapi.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<Review> getReviewsByArticleId(Pageable pageable, Long articleId);
    Page<Review> getReviewsByUserId(Pageable pageable, Long userId);
    Review getReviewById(Long id);
    Review addReview(Review review, Long articleId, Long userId);
    Review updateReviewById(Review review, Long id);
    void deleteReviewById(Long id);
}
