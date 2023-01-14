package com.example.blogapi.service.exception.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:error-message.properties")
public class ArticleErrorMessage {
    public static String NOT_FOUND;

    @Value("${article.not-found.message}")
    private void setNotFound(String value) {
        NOT_FOUND = value;
    }
}
