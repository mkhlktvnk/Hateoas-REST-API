package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.CategoryController;
import com.example.blogapi.web.model.CategoryModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface CategoryMapper extends ModelMapper<Category, CategoryModel> {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @AfterMapping
    default void addLinks(@MappingTarget CategoryModel categoryModel) {
        categoryModel.add(linkTo(methodOn(CategoryController.class)
                .getCategory(categoryModel.getId()))
                .withSelfRel());
        categoryModel.add(linkTo(methodOn(ArticleController.class)
                .getArticlesByCategoryId(null, categoryModel.getId()))
                .withRel("articles"));
    }
}
