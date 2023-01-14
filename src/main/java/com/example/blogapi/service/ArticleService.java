package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<Article> getArticlesByPaging(Pageable pageable);
    Article getArticleById(Long id);
    Article addArticle(Article article, User user);
}
