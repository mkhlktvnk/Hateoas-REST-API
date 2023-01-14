package com.example.blogapi.service.exception.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:error-message.properties")
public class CategoryErrorMessage {
    public static String NOT_FOUND;
    public static String NAME_DUPLICATION;

    @Value("${category.not-found.message}")
    private void setNotFound(String value) {
        NOT_FOUND = value;
    }

    @Value("${category.duplicate-name.message}")
    private void setNameDuplication(String value) {
        NAME_DUPLICATION = value;
    }

}
