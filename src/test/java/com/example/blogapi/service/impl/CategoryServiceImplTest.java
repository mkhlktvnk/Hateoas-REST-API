package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.repository.CategoryRepository;
import com.example.blogapi.service.exception.DuplicateEntityException;
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
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private static final Long ID = 1L;

    private static final String NAME = "name";

    @Test
    void getCategoriesByPaging_shouldCallRepositoryOnce() {
        Pageable pageable = mock(Pageable.class);
        when(categoryRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<Category> categories = categoryService.getCategoriesByPaging(pageable);

        assertNotNull(categories);
        assertEquals(categories, Page.empty());
        verify(categoryRepository, only()).findAll(pageable);
    }

    @Test
    void getCategoryById_shouldCallRepositoryOnceReturnActualCategory_whenPresent() {
        Category category = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));

        Category actual = categoryService.getCategoryById(ID);

        assertNotNull(actual);
        assertEquals(actual, category);
        verify(categoryRepository, only()).findById(ID);
    }

    @Test
    void getCategoryById_shouldThrowEntityNotFoundException_whenNotPresent() {
        when(categoryRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(ID));
    }

    @Test
    void addCategory_shouldReturnSavedCategoryAndCallRepositoryTwice_whenNameIsUnique() {
        Category category = mock(Category.class);
        when(category.getName()).thenReturn(NAME);
        when(categoryRepository.existsByName(category.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);

        Category saved = categoryService.addCategory(category);

        assertNotNull(saved);
        assertEquals(saved, category);
        verify(categoryRepository).existsByName(category.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void addCategory_shouldThrowDuplicateEntityException_whenNameIsNotUnique() {
        Category category = mock(Category.class);
        when(category.getName()).thenReturn(NAME);
        when(categoryRepository.existsByName(category.getName())).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> categoryService.addCategory(category));
    }

    @Test
    void updateCategoryById_shouldCallRepositoryAndUpdateCategory_whenCategoryIsPresent() {
        Category category = mock(Category.class);
        Category categoryToUpdate = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(categoryToUpdate));

        categoryService.updateCategoryById(category, ID);

        verify(categoryRepository).findById(ID);
        verify(categoryToUpdate).setName(category.getName());
        verify(categoryToUpdate).setDescription(category.getDescription());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategoryById_shouldThrowEntityNotFoundException_whenCategoryIsNotPresent() {
        when(categoryRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategoryById(any(Category.class), ID));
    }

    @Test
    void deleteCategoryById_shouldCallRepository_whenCategoryIsPresent() {
        when(categoryRepository.existsById(ID)).thenReturn(true);

        categoryService.deleteCategoryById(ID);

        verify(categoryRepository).existsById(ID);
        verify(categoryRepository).deleteById(ID);
    }

    @Test
    void deleteCategoryById_shouldThrowEntityNotFoundException_whenCategoryIsNotPresent() {
        when(categoryRepository.existsById(ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategoryById(ID));
    }
}