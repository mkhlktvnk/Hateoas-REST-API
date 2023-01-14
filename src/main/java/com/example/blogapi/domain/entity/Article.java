package com.example.blogapi.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "articles")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Article extends AbstractEntity implements Serializable {
    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @JoinColumn(name = "article_id")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> reviews;
}
