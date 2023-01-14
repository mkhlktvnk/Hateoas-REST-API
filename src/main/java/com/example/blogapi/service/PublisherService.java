package com.example.blogapi.service;

import com.example.blogapi.domain.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<Publisher> getUsersByPaging(Pageable pageable);
    Publisher getUserById(Long id);
    Publisher addUser(Publisher publisher);
    boolean existsById(Long id);
}
