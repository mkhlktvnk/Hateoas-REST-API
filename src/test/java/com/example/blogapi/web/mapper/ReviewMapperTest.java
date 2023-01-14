package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.web.model.ReviewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewMapperTest {
    private final ReviewMapper reviewMapper = ReviewMapper.INSTANCE;

    @Test
    void mapToModel_shouldMapAndAddLinks_whenArgIsNotNull() {
        Review review = mock(Review.class);
        Article article = mock(Article.class);
        Publisher publisher = mock(Publisher.class);
        when(review.getPublisher()).thenReturn(publisher);
        when(review.getArticle()).thenReturn(article);

        ReviewModel mapped = reviewMapper.mapToModel(review);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), review.getId());
        assertEquals(mapped.getMark(), review.getMark());
        assertEquals(mapped.getContent(), review.getContent());
        assertEquals(mapped.getLinks().stream().count(), 3);
        verify(review).getPublisher();
        verify(review).getArticle();
    }

    @Test
    void mapToModel_shouldReturnNull_whenArgIsNull() {
        assertNull(reviewMapper.mapToModel((Review) null));
    }

    @Test
    void mapToEntity_shouldMap_whenArgIsNotNull() {
        ReviewModel reviewModel = mock(ReviewModel.class);

        Review mapped = reviewMapper.mapToEntity(reviewModel);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), reviewModel.getId());
        assertEquals(mapped.getMark(), reviewModel.getMark());
        assertEquals(mapped.getContent(), reviewModel.getContent());
    }

    @Test
    void mapToEntity_shouldReturnNull_whenArgIsNull() {
        assertNull(reviewMapper.mapToEntity((ReviewModel) null));
    }
}