package com.example.blogapi.domain.repository;

import com.example.blogapi.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
    Page<Review> findAllByArticleId(Pageable pageable, Long articleId);
    Page<Review> findAllByPublisherId(Pageable pageable, Long publisherId);
}
