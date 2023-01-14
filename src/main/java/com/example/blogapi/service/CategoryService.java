package com.example.blogapi.service;

import com.example.blogapi.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> getCategoriesByPaging(Pageable pageable);
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategoryById(Category category, Long id);
    void deleteCategoryById(Long id);
    boolean existsById(Long id);
}
