package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.web.model.CategoryModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CategoryMapperTest {
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void mapToModel_shouldMapAndAddLinks_whenArgIsNotNull() {
        Category category = mock(Category.class);

        CategoryModel mapped = categoryMapper.mapToModel(category);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), category.getId());
        assertEquals(mapped.getName(), category.getName());
        assertEquals(mapped.getDescription(), category.getDescription());
        assertEquals(mapped.getLinks().stream().count(), 2);
    }

    @Test
    void mapToModel_shouldReturnNull_whenArgIsNotNull() {
        assertNull(categoryMapper.mapToModel((Category) null));
    }

    @Test
    void mapToEntity_shouldMap() {
        CategoryModel categoryModel = mock(CategoryModel.class);

        Category mapped = categoryMapper.mapToEntity(categoryModel);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), categoryModel.getId());
        assertEquals(mapped.getName(), categoryModel.getName());
        assertEquals(mapped.getDescription(), categoryModel.getDescription());
    }

    @Test
    void mapToEntity_shouldReturnNull_whenArgIsNull() {
        assertNull(categoryMapper.mapToEntity((CategoryModel) null));
    }
}