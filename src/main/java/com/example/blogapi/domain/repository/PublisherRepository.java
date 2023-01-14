package com.example.blogapi.domain.repository;

import com.example.blogapi.domain.entity.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long>, PagingAndSortingRepository<Publisher, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
