package com.learn.sanskar.udemy.loans.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    @JsonProperty("status")
    private final Integer status;
    @JsonProperty("error")
    private final String error;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("stackTrace")
    private String stackTrace;
    @JsonProperty("path")
    private final String path;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("errors")
    private List<ValidationErrors> errors;

    @RequiredArgsConstructor
    static class ValidationErrors {
        @JsonProperty("field")
        private final String field;
        @JsonProperty("message")
        private final String message;
    }
}
