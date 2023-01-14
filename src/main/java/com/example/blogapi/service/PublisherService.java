package com.example.blogapi.service;

import com.example.blogapi.domain.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {
    Page<Publisher> getAllPublishers(Pageable pageable);
    Publisher getPublisherById(Long id);
    Publisher addPublisher(Publisher publisher);
    Publisher updatePublisher(Publisher publisher, Long id);
    void deletePublisherById(Long id);
    boolean existsById(Long id);
}
