package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.ReviewRepository;
import com.example.blogapi.service.ArticleService;
import com.example.blogapi.service.ReviewService;
import com.example.blogapi.service.PublisherService;
import com.example.blogapi.service.exception.EntityNotFoundException;
import com.example.blogapi.service.exception.message.ArticleErrorMessage;
import com.example.blogapi.service.exception.message.PublisherErrorMessage;
import com.example.blogapi.service.exception.message.ReviewErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PublisherService publisherService;
    private final ArticleService articleService;

    @Override
    public Page<Review> getReviewsByArticleId(Pageable pageable, Long articleId) {
        if (!articleService.existsById(articleId)) {
            log.error(ArticleErrorMessage.NOT_FOUND + "ID: " + articleId);
            throw new EntityNotFoundException(ArticleErrorMessage.NOT_FOUND);
        }
        return reviewRepository.findAllByArticleId(pageable, articleId);
    }

    @Override
    @Transactional
    public Page<Review> getReviewsByUserId(Pageable pageable, Long publisherId) {
        if (!publisherService.existsById(publisherId)) {
            log.error(ArticleErrorMessage.NOT_FOUND + "ID: " + publisherId);
            throw new EntityNotFoundException(PublisherErrorMessage.NOT_FOUND);
        }
        return reviewRepository.findAllByPublisherId(pageable, publisherId);
    }

    @Override
    @Cacheable(cacheNames = "review", key = "#id")
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ReviewErrorMessage.NOT_FOUND));
    }

    @Override
    @Transactional
    public Review addReview(Review review, Long articleId, Long userId) {
        Article article = articleService.getArticleById(articleId);
        Publisher publisher = publisherService.getPublisherById(userId);
        review.setArticle(article);
        review.setPublisher(publisher);
        return reviewRepository.save(review);
    }

    @Override
    @CachePut(cacheNames = "review", key = "#id")
    public Review updateReviewById(Review review, Long id) {
        Review reviewToUpdate = getReviewById(id);
        reviewToUpdate.setMark(review.getMark());
        reviewToUpdate.setContent(review.getContent());
        return reviewRepository.save(reviewToUpdate);
    }

    @Override
    @CacheEvict(cacheNames = "review", key = "#id")
    public void deleteReviewById(Long id) {
        checkForReviewExistenceById(id);
        reviewRepository.deleteById(id);
    }

    private void checkForReviewExistenceById(Long id) {
        if (!reviewRepository.existsById(id)) {
            log.error(ReviewErrorMessage.NOT_FOUND + " ID: " + id);
            throw new EntityNotFoundException(ReviewErrorMessage.NOT_FOUND);
        }
    }
}
