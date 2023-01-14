package com.example.blogapi.service.exception.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:error-message.properties")
public class PublisherErrorMessage {
    public static String NOT_FOUND;

    public static String USERNAME_DUPLICATION;

    public static String EMAIL_DUPLICATION;

    @Value("${publisher.not-found.message}")
    private void setNotFound(String value) {
        NOT_FOUND = value;
    }

    @Value("${publisher.duplicate-username.message}")
    private void setUsernameDuplication(String value) {
        USERNAME_DUPLICATION = value;
    }

    @Value("${publisher.duplicate-email.message}")
    private void setEmailDuplication(String value) {
        EMAIL_DUPLICATION = value;
    }
}
