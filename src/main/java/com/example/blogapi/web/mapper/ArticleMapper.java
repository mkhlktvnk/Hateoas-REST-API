package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.CategoryController;
import com.example.blogapi.web.controller.PublisherController;
import com.example.blogapi.web.model.ArticleModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface ArticleMapper extends ModelMapper<Article, ArticleModel> {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @AfterMapping
    default void addLinks(@MappingTarget ArticleModel articleModel, Article article) {
        articleModel.add(linkTo(methodOn(ArticleController.class)
                .getArticle(articleModel.getId()))
                .withSelfRel());
        articleModel.add(linkTo(methodOn(PublisherController.class)
                .getPublisher(article.getPublisher().getId()))
                .withRel("publisher"));
        articleModel.add(linkTo(methodOn(CategoryController.class)
                .getCategory(article.getCategory().getId()))
                .withRel("category"));
        articleModel.add(linkTo(methodOn(ArticleController.class)
                .getArticlesByUserId(null, articleModel.getId()))
                .withRel("reviews"));
    }
}
