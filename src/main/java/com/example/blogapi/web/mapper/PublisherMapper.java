package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.PublisherController;
import com.example.blogapi.web.controller.ReviewController;
import com.example.blogapi.web.model.PublisherModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface PublisherMapper extends ModelMapper<Publisher, PublisherModel> {
    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    @AfterMapping
    default void addLinks(@MappingTarget PublisherModel publisherModel) {
        publisherModel.add(linkTo(methodOn(PublisherController.class)
                .getPublisher(publisherModel.getId()))
                .withSelfRel());
        publisherModel.add(linkTo(methodOn(ArticleController.class)
                .getArticlesByUserId(null, publisherModel.getId()))
                .withRel("articles"));
        publisherModel.add(linkTo(methodOn(ReviewController.class)
                .getReviewsByUserId(null, publisherModel.getId()))
                .withRel("reviews"));
    }
}
