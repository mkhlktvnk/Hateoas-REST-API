package com.example.blogapi.web.assembler;

import com.example.blogapi.domain.entity.AbstractEntity;
import com.example.blogapi.web.mapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class ModelAssembler<E extends AbstractEntity, M extends RepresentationModel<M>, C>
        extends RepresentationModelAssemblerSupport<E, M> {
    private final ModelMapper<E, M> mapper;
    public ModelAssembler(Class<?> controllerClass, Class<M> resourceType, ModelMapper<E, M> mapper) {
        super(controllerClass, resourceType);
        this.mapper = mapper;
    }

    @Override
    public M toModel(E entity) {
        return mapper.mapToModel(entity);
    }
}
