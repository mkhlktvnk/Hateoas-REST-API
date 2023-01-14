package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.ReviewRepository;
import com.example.blogapi.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PublisherServiceImpl userService;

    @Mock
    private ArticleServiceImpl articleService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private static final Long REVIEW_ID = 1L;

    private static final Long ARTICLE_ID = 2L;

    private static final Long PUBLISHER_ID = 3L;

    @Test

    void getReviewsByArticleId_shouldCallRepositoryAndArticleService_whenArticleIsPresent() {
        Pageable pageRequest = mock(Pageable.class);
        when(reviewRepository.findAllByArticleId(pageRequest, ARTICLE_ID)).thenReturn(Page.empty());
        when(articleService.existsById(ARTICLE_ID)).thenReturn(true);

        reviewService.getReviewsByArticleId(pageRequest, ARTICLE_ID);

        verify(articleService).existsById(ARTICLE_ID);
        verify(reviewRepository).findAllByArticleId(pageRequest, ARTICLE_ID);
    }

    @Test
    void getReviewsByArticleId_shouldThrowEntityNotFoundException_whenArticleIsNotPresent() {
        Pageable pageRequest = mock(Pageable.class);
        when(articleService.existsById(ARTICLE_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> reviewService.getReviewsByArticleId(pageRequest, ARTICLE_ID));
    }

    @Test
    void getReviewsByUserId_shouldCallRepositoryAndUserService_whenUserIsPresent() {
        Pageable pageRequest = mock(Pageable.class);
        when(reviewRepository.findAllByPublisherId(pageRequest, PUBLISHER_ID)).thenReturn(Page.empty());
        when(userService.existsById(PUBLISHER_ID)).thenReturn(true);

        reviewService.getReviewsByUserId(pageRequest, PUBLISHER_ID);

        verify(userService).existsById(PUBLISHER_ID);
        verify(reviewRepository).findAllByPublisherId(pageRequest, PUBLISHER_ID);
    }

    @Test
    void getReviewsById_shouldCallRepositoryAndReturnActualReview_whenReviewIsPresent() {
        Review review = mock(Review.class);
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.of(review));

        Review actual = reviewService.getReviewById(REVIEW_ID);

        assertNotNull(actual);
        assertEquals(actual, review);
        verify(reviewRepository, only()).findById(REVIEW_ID);
    }

    @Test
    void getReviewsById_shouldThrowEntityNotFoundException_whenReviewIsNotPresent() {
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.getReviewById(REVIEW_ID));
    }

    @Test
    void addReview_shouldReturnSavedReviewAndCallAllMembers_whenArticleAndUserArePresent() {
        Article article = mock(Article.class);
        Publisher publisher = mock(Publisher.class);
        Review review = mock(Review.class);
        when(articleService.getArticleById(ARTICLE_ID)).thenReturn(article);
        when(userService.getPublisherById(PUBLISHER_ID)).thenReturn(publisher);
        when(reviewRepository.save(review)).thenReturn(review);

        Review saved = reviewService.addReview(review, ARTICLE_ID, PUBLISHER_ID);

        assertNotNull(saved);
        assertEquals(saved, review);
        verify(articleService).getArticleById(ARTICLE_ID);
        verify(userService).getPublisherById(PUBLISHER_ID);
        verify(review).setArticle(article);
        verify(review).setPublisher(publisher);
        verify(reviewRepository).save(review);
    }

    @Test
    void updateReview_shouldCallRepositoryAndUpdateReview_WhenReviewIsPresent() {
        Review review = mock(Review.class);
        Review reviewToUpdate = mock(Review.class);
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.of(review));

        reviewService.updateReviewById(reviewToUpdate, REVIEW_ID);

        verify(reviewRepository).findById(REVIEW_ID);
        verify(review).setMark(reviewToUpdate.getMark());
        verify(review).setContent(reviewToUpdate.getContent());
        verify(reviewRepository).save(review);
    }

    @Test
    void updateReview_shouldThrowEntityNotFoundException_WhenReviewIsNotPresent() {
        Review review = mock(Review.class);
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.updateReviewById(review, REVIEW_ID));
    }

    @Test
    void deleteReviewById_shouldCallRepository_whenReviewIsPresent() {
        when(reviewRepository.existsById(REVIEW_ID)).thenReturn(true);

        reviewService.deleteReviewById(REVIEW_ID);

        verify(reviewRepository).existsById(REVIEW_ID);
        verify(reviewRepository).deleteById(REVIEW_ID);
    }

    @Test
    void deleteReviewById_shouldThrowEntityNotFoundException_whenReviewIsNotPresent() {
        when(reviewRepository.existsById(REVIEW_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> reviewService.deleteReviewById(REVIEW_ID));
    }
}