package com.example.blogapi.web.config;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.web.assembler.ModelAssembler;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.CategoryController;
import com.example.blogapi.web.controller.ReviewController;
import com.example.blogapi.web.controller.PublisherController;
import com.example.blogapi.web.mapper.ArticleMapper;
import com.example.blogapi.web.mapper.CategoryMapper;
import com.example.blogapi.web.mapper.ReviewMapper;
import com.example.blogapi.web.mapper.PublisherMapper;
import com.example.blogapi.web.model.ArticleModel;
import com.example.blogapi.web.model.CategoryModel;
import com.example.blogapi.web.model.PublisherModel;
import com.example.blogapi.web.model.ReviewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelAssemblerConfig {
    @Bean
    public ModelAssembler<Publisher, PublisherModel, PublisherController> publisherModelAssembler() {
        return new ModelAssembler<>(PublisherController.class, PublisherModel.class, PublisherMapper.INSTANCE);
    }

    @Bean
    public ModelAssembler<Category, CategoryModel, CategoryController> categoryModelAssembler() {
        return new ModelAssembler<>(CategoryController.class, CategoryModel.class, CategoryMapper.INSTANCE);
    }

    @Bean
    public ModelAssembler<Article, ArticleModel, ArticleController> articleModelAssembler() {
        return new ModelAssembler<>(ArticleController.class, ArticleModel.class, ArticleMapper.INSTANCE);
    }

    @Bean
    public ModelAssembler<Review, ReviewModel, ReviewController> reviewModelAssembler() {
        return new ModelAssembler<>(ReviewController.class, ReviewModel.class, ReviewMapper.INSTANCE);
    }
}
