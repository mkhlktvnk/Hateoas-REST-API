package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.web.model.ArticleModel;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleMapperTest {
    private final ArticleMapper articleMapper = ArticleMapper.INSTANCE;

    private static final Long ARTICLE_ID = 1L;

    private static final Long PUBLISHER_ID = 2L;

    private static final Long CATEGORY_ID = 3L;

    @Test
    void mapToModel_shouldMapAndAddLinks_whenArgIsNotNull() {
        Article article = mock(Article.class);
        Publisher publisher = mock(Publisher.class);
        Category category = mock(Category.class);

        when(article.getId()).thenReturn(ARTICLE_ID);
        when(article.getPublisher()).thenReturn(publisher);
        when(article.getCategory()).thenReturn(category);

        when(publisher.getId()).thenReturn(PUBLISHER_ID);
        when(category.getId()).thenReturn(CATEGORY_ID);

        ArticleModel mapped = articleMapper.mapToModel(article);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), article.getId());
        assertEquals(mapped.getDescription(), article.getDescription());
        assertEquals(mapped.getContent(), article.getContent());
        assertEquals(mapped.getLinks().stream().count(), 3);
    }

    @Test
    void mapToModel_shouldReturnNull_whenArgIsNull() {
        assertNull(articleMapper.mapToModel((Article) null));
    }

    @Test
    void mapToEntity_shouldMap_whenArgIsNotNull() {
        ArticleModel articleModel = mock(ArticleModel.class);

        Article mapped = articleMapper.mapToEntity(articleModel);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), articleModel.getId());
        assertEquals(mapped.getTopic(), articleModel.getTopic());
        assertEquals(mapped.getDescription(), articleModel.getDescription());
        assertEquals(mapped.getContent(), articleModel.getContent());
    }

    @Test
    void mapToEntity_shouldReturnNull_whenArgIsNull() {
        assertNull(articleMapper.mapToEntity((ArticleModel) null));
    }
}