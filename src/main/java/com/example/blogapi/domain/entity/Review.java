package com.example.blogapi.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Review extends AbstractEntity implements Serializable {
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Publisher publisher;
}
