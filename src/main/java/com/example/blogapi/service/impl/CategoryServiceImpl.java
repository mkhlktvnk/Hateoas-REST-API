package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.repository.CategoryRepository;
import com.example.blogapi.service.CategoryService;
import com.example.blogapi.service.exception.DuplicateEntityException;
import com.example.blogapi.service.exception.EntityNotFoundException;
import com.example.blogapi.service.exception.message.CategoryErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategoriesByPaging(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "category", key = "#id")
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CategoryErrorMessage.NOT_FOUND));
    }

    @Override
    @Transactional
    public Category addCategory(Category category) {
        checkForCategoryNameUniqueness(category.getName());
        return categoryRepository.save(category);
    }

    @Override
    @CachePut(cacheNames = "category", key = "#id")
    public Category updateCategoryById(Category category, Long id) {
        Category categoryToUpdate = getCategoryById(id);
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    @CacheEvict(cacheNames = "category", key = "#id")
    public void deleteCategoryById(Long id) {
        checkForCategoryExistenceById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    private void checkForCategoryExistenceById(Long id) {
        if (!categoryRepository.existsById(id)) {
            log.error(CategoryErrorMessage.NOT_FOUND + " Id: " + id);
            throw new EntityNotFoundException(CategoryErrorMessage.NOT_FOUND);
        }
    }

    private void checkForCategoryNameUniqueness(String name) {
        if (categoryRepository.existsByName(name)) {
            log.error(CategoryErrorMessage.NAME_DUPLICATION + " Name: " + name);
            throw new DuplicateEntityException(CategoryErrorMessage.NAME_DUPLICATION);
        }
    }
}
