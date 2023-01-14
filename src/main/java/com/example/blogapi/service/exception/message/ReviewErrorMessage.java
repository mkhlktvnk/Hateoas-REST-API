package com.example.blogapi.service.exception.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:error-message.properties")
public class ReviewErrorMessage {
    public static String NOT_FOUND;

    @Value("${review.not-found.message}")
    private void setNotFound(String value) {
        NOT_FOUND = value;
    }
}
