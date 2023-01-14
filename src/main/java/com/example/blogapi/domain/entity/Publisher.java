package com.example.blogapi.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JoinColumn(name = "user_id")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> reviews;
}
