package com.example.blogapi.domain.repository;

import com.example.blogapi.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends CrudRepository<Article, Long>, PagingAndSortingRepository<Article, Long> {
    Page<Article> findAllByPublisherId(Pageable pageable, Long publisherId);
    Page<Article> findAllByCategoryId(Pageable pageable, Long categoryId);
    boolean existsByCategoryId(Long categoryId);
}
