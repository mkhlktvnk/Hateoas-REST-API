package com.example.blogapi.domain.repository;

import com.example.blogapi.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {
    boolean existsByName(String name);
}
