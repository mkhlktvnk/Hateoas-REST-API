package com.example.blogapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    @JsonProperty("message")
    private String message;

    @JsonProperty("contextPath")
    private String contextPath;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("errors")
    private List<String> errors;
}
