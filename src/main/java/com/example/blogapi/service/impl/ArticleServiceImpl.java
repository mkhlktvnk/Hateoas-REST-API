package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.ArticleRepository;
import com.example.blogapi.service.ArticleService;
import com.example.blogapi.service.CategoryService;
import com.example.blogapi.service.PublisherService;
import com.example.blogapi.service.exception.EntityNotFoundException;
import com.example.blogapi.service.exception.message.ArticleErrorMessage;
import com.example.blogapi.service.exception.message.CategoryErrorMessage;
import com.example.blogapi.service.exception.message.PublisherErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final PublisherService publisherService;
    private final CategoryService categoryService;

    @Override
    public Page<Article> getArticlesByPaging(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Page<Article> getArticlesByPublisherId(Pageable pageable, Long publisherId) {
        if (!publisherService.existsById(publisherId)) {
            throw new EntityNotFoundException(PublisherErrorMessage.NOT_FOUND);
        }
        return articleRepository.findAllByPublisherId(pageable, publisherId);
    }

    @Override
    @Transactional
    public Page<Article> getArticlesByCategoryId(Pageable pageable, Long categoryId) {
        if (!categoryService.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryErrorMessage.NOT_FOUND);
        }
        return articleRepository.findAllByCategoryId(pageable, categoryId);
    }

    @Override
    @Cacheable(cacheNames = "articles", key = "#id")
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ArticleErrorMessage.NOT_FOUND));
    }

    @Override
    @Transactional
    public Article addArticle(Article article, Long userId, Long categoryId) {
        Publisher publisher = publisherService.getPublisherById(userId);
        Category category = categoryService.getCategoryById(categoryId);
        article.setPublisher(publisher);
        article.setCategory(category);
        return articleRepository.save(article);
    }

    @Override
    @CachePut(cacheNames = "article", key = "#articleId")
    public Article updateArticleById(Long articleId, Article newArticle) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ArticleErrorMessage.NOT_FOUND));
        article.setTopic(newArticle.getTopic());
        article.setDescription(newArticle.getDescription());
        article.setContent(newArticle.getContent());
        return articleRepository.save(article);
    }

    @Override
    @CacheEvict(cacheNames = "article", key = "#id")
    public void deleteArticleById(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException(ArticleErrorMessage.NOT_FOUND);
        }
        articleRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return articleRepository.existsById(id);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return articleRepository.existsByCategoryId(categoryId);
    }
}
