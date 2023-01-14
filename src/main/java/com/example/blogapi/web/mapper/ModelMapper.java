package com.example.blogapi.web.mapper;

import ch.qos.logback.core.model.Model;
import com.example.blogapi.domain.entity.AbstractEntity;

import java.util.Collection;
import java.util.List;

public interface ModelMapper<E extends AbstractEntity, M> {
    M mapToModel(E entity);
    List<Model> mapToModel(Collection<E> entites);
    E mapToEntity(M model);
}
