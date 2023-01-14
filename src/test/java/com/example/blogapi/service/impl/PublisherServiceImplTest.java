package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.PublisherRepository;
import com.example.blogapi.service.exception.DuplicateEntityException;
import com.example.blogapi.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private static final Long ID = 1L;

    private static final String EMAIL = "email";

    private static final String USERNAME = "username";

    @Test
    void getAllPublishers_shouldCallRepository() {
        Pageable page = mock(Pageable.class);
        when(publisherRepository.findAll(page)).thenReturn(Page.empty());

        Page<Publisher> userPage = publisherService.getAllPublishers(page);

        assertNotNull(userPage);
        verify(publisherRepository, only()).findAll(page);
    }

    @Test
    void getPublisherById_shouldCallRepositoryAndReturnActualPublisher_whenPresent() {
        Publisher publisher = mock(Publisher.class);
        when(publisherRepository.findById(ID)).thenReturn(Optional.of(publisher));

        Publisher actual = publisherService.getPublisherById(ID);

        assertNotNull(actual);
        assertEquals(actual, publisher);
        verify(publisherRepository, only()).findById(ID);
    }

    @Test
    void getPublisherById_shouldThrowEntityNotFoundException_whenEmpty() {
        when(publisherRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> publisherService.getPublisherById(ID));
    }

    @Test
    void addPublisher_shouldCallRepositoryAndReturnCreatedPublisher_whenUsernameAndEmailAreUnique() {
        Publisher publisher = mock(Publisher.class);
        when(publisher.getUsername()).thenReturn(USERNAME);
        when(publisher.getEmail()).thenReturn(EMAIL);
        when(publisherRepository.existsByUsername(publisher.getUsername())).thenReturn(false);
        when(publisherRepository.existsByEmail(publisher.getEmail())).thenReturn(false);
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher actual = publisherService.addUser(publisher);

        assertNotNull(actual);
        assertEquals(actual, publisher);
        verify(publisherRepository).existsByEmail(publisher.getEmail());
        verify(publisherRepository).existsByUsername(publisher.getUsername());
        verify(publisherRepository).save(publisher);
    }

    @Test
    void addPublisher_shouldThrowDuplicateEntityException_whenEmailNotUnique() {
        Publisher publisher = mock(Publisher.class);
        when(publisher.getEmail()).thenReturn(EMAIL);
        when(publisherRepository.existsByEmail(publisher.getEmail())).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> publisherService.addUser(publisher));
    }

    @Test
    void addPublisher_shouldThrowDuplicateEntityException_whenUsernameNotUnique() {
        Publisher publisher = mock(Publisher.class);
        when(publisher.getUsername()).thenReturn(USERNAME);
        when(publisher.getEmail()).thenReturn(EMAIL);
        when(publisherRepository.existsByEmail(publisher.getEmail())).thenReturn(false);
        when(publisherRepository.existsByUsername(publisher.getUsername())).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> publisherService.addUser(publisher));
    }

    @Test
    void updatePublisher_shouldCallRepositoryAndUpdatePublisher_whenPublisherIsPresent() {
        Publisher publisher = mock(Publisher.class);
        Publisher newPublisher = mock(Publisher.class);
        when(publisherRepository.findById(ID)).thenReturn(Optional.of(publisher));

        publisherService.updatePublisher(newPublisher, ID);

        verify(publisherRepository).findById(ID);
        verify(publisher).setEmail(newPublisher.getEmail());
        verify(publisher).setUsername(newPublisher.getUsername());
        verify(publisherRepository).save(publisher);
    }

    @Test
    void deletePublisherById_shouldCallRepository_whenPublisherIsPresent() {
        when(publisherRepository.existsById(ID)).thenReturn(true);

        publisherService.deletePublisherById(ID);

        verify(publisherRepository).existsById(ID);
        verify(publisherRepository).deleteById(ID);
    }

    @Test
    void deletePublisherById_shouldThrowEntityNotFoundException_whenPublisherIsNotPresent() {
        when(publisherRepository.existsById(ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> publisherService.deletePublisherById(ID));
    }
}