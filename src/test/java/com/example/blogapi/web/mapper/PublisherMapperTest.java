package com.example.blogapi.web.mapper;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.web.model.PublisherModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PublisherMapperTest {
    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Test
    void mapToModel_shouldMapAndAddLinks_whenArgIsNotNull() {
        Publisher publisher = mock(Publisher.class);

        PublisherModel mapped = publisherMapper.mapToModel(publisher);

        assertNotNull(mapped);
        assertEquals(mapped.getId(), publisher.getId());
        assertEquals(mapped.getUsername(), publisher.getUsername());
        assertEquals(mapped.getEmail(), publisher.getEmail());
        assertEquals(mapped.getLinks().stream().count(), 3);
    }

    @Test
    void mapToModel_shouldReturnNull_whenArgIsNull() {
        assertNull(publisherMapper.mapToModel((Publisher) null));
    }
}