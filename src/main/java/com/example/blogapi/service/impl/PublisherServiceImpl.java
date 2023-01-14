package com.example.blogapi.service.impl;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.domain.repository.UserRepository;
import com.example.blogapi.service.UserService;
import com.example.blogapi.service.exception.DuplicateEntityException;
import com.example.blogapi.service.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<Publisher> getUsersByPaging(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Publisher getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found! ID: " + id));
    }

    @Override
    @Transactional
    public Publisher addUser(Publisher publisher) {
        if (userRepository.existsByEmail(publisher.getEmail())) {
            throw new DuplicateEntityException("Error! User already exists with email: " + publisher.getEmail());
        }
        if (userRepository.existsByUsername(publisher.getUsername())) {
            throw new DuplicateEntityException("Error! User already exists with username: " + publisher.getUsername());
        }
        return userRepository.save(publisher);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
