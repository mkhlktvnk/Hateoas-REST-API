package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.ArticleRepository;
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
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private PublisherServiceImpl userService;

    @Mock
    private CategoryServiceImpl categoryService;

    private static final Long ID = 1L;

    private static final Long USER_ID = 2L;

    private static final Long CATEGORY_ID = 3L;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getArticlesByPaging_shouldCallRepositoryOnce() {
        Pageable pageable = mock(Pageable.class);
        when(articleRepository.findAll(pageable)).thenReturn(Page.empty());

        articleService.getArticlesByPaging(pageable);

        verify(articleRepository, only()).findAll(pageable);
    }

    @Test
    void getArticleById_shouldReturnActualArticle_whenPresent() {
        Article article = mock(Article.class);
        when(articleRepository.findById(ID)).thenReturn(Optional.of(article));

        Article actual = articleService.getArticleById(ID);

        assertNotNull(actual);
        assertEquals(actual, article);
        verify(articleRepository, only()).findById(ID);
    }

    @Test
    void addArticle_shouldReturnCreatedArticleAndCallAllMembers() {
        Article article = mock(Article.class);
        Publisher publisher = mock(Publisher.class);
        Category category = mock(Category.class);
        when(userService.getPublisherById(USER_ID)).thenReturn(publisher);
        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category);
        when(articleRepository.save(article)).thenReturn(article);

        Article saved = articleService.addArticle(article, USER_ID, CATEGORY_ID);

        assertNotNull(saved);
        assertEquals(saved, article);
        assertEquals(saved.getPublisher(), article.getPublisher());
        assertEquals(saved.getCategory(), article.getCategory());
        verify(userService).getPublisherById(USER_ID);
        verify(categoryService).getCategoryById(CATEGORY_ID);
        verify(article).setPublisher(publisher);
        verify(article).setCategory(category);
        verify(articleRepository).save(article);
    }

    @Test
    void getArticlesByUserId_shouldCallUserServiceAndArticleRepository_whenUserIsPresent() {
        Pageable pageable = mock(Pageable.class);
        when(userService.existsById(USER_ID)).thenReturn(true);
        when(articleRepository.findAllByPublisherId(pageable, USER_ID)).thenReturn(Page.empty());

        articleService.getArticlesByUserId(pageable, USER_ID);

        verify(userService).existsById(USER_ID);
        verify(articleRepository).findAllByPublisherId(pageable, USER_ID);
    }

    @Test
    void getArticlesByUserId_shouldThrowEntityNotFoundException_whenUserIsNotPresent() {
        Pageable pageable = mock(Pageable.class);
        when(userService.existsById(USER_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> articleService.getArticlesByUserId(pageable, USER_ID));
    }

    @Test
    void getArticlesByCategoryId_shouldCallCategoryServiceAndArticleRepository_whenCategoryIsPresent() {
        Pageable pageable = mock(Pageable.class);
        when(categoryService.existsById(CATEGORY_ID)).thenReturn(true);
        when(articleRepository.findAllByCategoryId(pageable, CATEGORY_ID)).thenReturn(Page.empty());

        articleService.getArticlesByCategoryId(pageable, CATEGORY_ID);

        verify(categoryService).existsById(CATEGORY_ID);
        verify(articleRepository).findAllByCategoryId(pageable, CATEGORY_ID);
    }

    @Test
    void getArticlesByCategoryId_shouldThrowEntityNotFoundException_whenCategoryIsNotPresent() {
        Pageable pageable = mock(Pageable.class);
        when(categoryService.existsById(CATEGORY_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> articleService.getArticlesByCategoryId(pageable, CATEGORY_ID));
    }

    @Test
    void deleteArticleById_shouldCallRepositoryTwice_whenArticleIsPresent() {
        when(articleRepository.existsById(ID)).thenReturn(true);

        articleService.deleteArticleById(ID);

        verify(articleRepository).existsById(ID);
        verify(articleRepository).deleteById(ID);
    }

    @Test
    void deleteArticleById_shouldThrowEntityNotFoundException_whenArticleIsNotPresent() {
        when(articleRepository.existsById(ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> articleService.deleteArticleById(ID));
    }

    @Test
    void updateArticleById_shouldUpdateAndReturnUpdatedArticle_whenArticleIsPresent() {
        Article newArticle = mock(Article.class);
        Article article = mock(Article.class);
        when(articleRepository.findById(ID)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);

        Article updated = articleService.updateArticleById(ID, newArticle);

        assertNotNull(article);
        assertEquals(updated.getTopic(), newArticle.getTopic());
        assertEquals(updated.getDescription(), newArticle.getDescription());
        assertEquals(updated.getContent(), newArticle.getContent());
        verify(articleRepository).findById(ID);
        verify(article).setTopic(newArticle.getTopic());
        verify(article).setDescription(newArticle.getDescription());
        verify(article).setContent(newArticle.getContent());
        verify(articleRepository).save(article);
    }

    @Test
    void updateArticleById_shouldThrowEntityNotFoundException_whenArticleIsNotPresent() {
        Article newArticle = mock(Article.class);
        when(articleRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> articleService.updateArticleById(ID, newArticle));
    }
}