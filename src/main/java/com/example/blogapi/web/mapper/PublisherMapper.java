package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.web.controller.ArticleController;
import com.example.blogapi.web.controller.PublisherController;
import com.example.blogapi.web.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface UserMapper extends ModelMapper<Publisher, UserModel> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @AfterMapping
    default void addLinks(@MappingTarget UserModel userModel) {
        userModel.add(linkTo(methodOn(PublisherController.class)
                .getPublisher(userModel.getId()))
                .withSelfRel());
        userModel.add(linkTo(methodOn(ArticleController.class)
                .getArticlesByUserId(null, userModel.getId()))
                .withRel("articles"));
    }
}
