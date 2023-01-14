package com.example.blogapi.service;

import com.example.blogapi.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<Article> getArticlesByPaging(Pageable pageable);
    Page<Article> getArticlesByPublisherId(Pageable pageable, Long userId);
    Page<Article> getArticlesByCategoryId(Pageable pageable, Long categoryId);
    Article getArticleById(Long id);
    Article addArticle(Article article, Long userId, Long categoryId);
    Article updateArticleById(Long articleId, Article newArticle);
    void deleteArticleById(Long id);
    boolean existsById(Long id);
    boolean existsByCategoryId(Long categoryId);
}
