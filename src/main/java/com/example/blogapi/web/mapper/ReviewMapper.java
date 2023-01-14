package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.ReviewController;
import com.example.blogapi.web.controller.PublisherController;
import com.example.blogapi.web.model.ReviewModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface ReviewMapper extends ModelMapper<Review, ReviewModel> {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @AfterMapping
    default void addLinks(@MappingTarget ReviewModel reviewModel, Review review) {
        reviewModel.add(linkTo(methodOn(ReviewController.class)
                .getReviewById(reviewModel.getId()))
                .withSelfRel());
        reviewModel.add(linkTo(methodOn(ArticleController.class)
                .getArticle(review.getArticle().getId()))
                .withRel("article"));
        reviewModel.add(linkTo(methodOn(PublisherController.class)
                .getPublisher(review.getPublisher().getId()))
                .withRel("publisher"));
    }
}
