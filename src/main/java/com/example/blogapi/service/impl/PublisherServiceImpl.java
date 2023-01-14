package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.PublisherRepository;
import com.example.blogapi.service.PublisherService;
import com.example.blogapi.service.exception.DuplicateEntityException;
import com.example.blogapi.service.exception.EntityNotFoundException;
import com.example.blogapi.service.exception.message.PublisherErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    @Override
    public Page<Publisher> getAllPublishers(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "publisher", key = "#id")
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PublisherErrorMessage.NOT_FOUND));
    }

    @Override
    @Transactional
    public Publisher addPublisher(Publisher publisher) {
        checkForPublisherEmailUniqueness(publisher.getEmail());
        checkForPublisherUsernameUniqueness(publisher.getUsername());
        return publisherRepository.save(publisher);
    }

    @Override
    @CachePut(cacheNames = "publisher", key = "#id")
    public Publisher updatePublisher(Publisher publisher, Long id) {
        Publisher publisherToUpdate = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PublisherErrorMessage.NOT_FOUND));
        publisherToUpdate.setUsername(publisher.getUsername());
        publisherToUpdate.setEmail(publisher.getEmail());
        return publisherRepository.save(publisherToUpdate);
    }

    @Override
    @CacheEvict(cacheNames = "publisher", key = "#id")
    public void deletePublisherById(Long id) {
        checkForPublisherExistenceById(id);
        publisherRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return publisherRepository.existsById(id);
    }

    private void checkForPublisherExistenceById(Long id) {
        if (!publisherRepository.existsById(id)) {
            log.error(PublisherErrorMessage.NOT_FOUND + " Id: " + id);
            throw new EntityNotFoundException(PublisherErrorMessage.NOT_FOUND);
        }
    }

    private void checkForPublisherEmailUniqueness(String email) {
        if (publisherRepository.existsByEmail(email)) {
            log.error(PublisherErrorMessage.EMAIL_DUPLICATION + " Email: " + email);
            throw new DuplicateEntityException(PublisherErrorMessage.EMAIL_DUPLICATION);
        }
    }

    private void checkForPublisherUsernameUniqueness(String username) {
        if (publisherRepository.existsByUsername(username)) {
            log.error(PublisherErrorMessage.USERNAME_DUPLICATION + " Username: " + username);
            throw new DuplicateEntityException(PublisherErrorMessage.USERNAME_DUPLICATION);
        }
    }
}
