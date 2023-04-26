package com.learn.sanskar.udemy.loans.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${exception.trace.capture}")
    private Boolean stackTrace;
    HttpServletRequest httpServletRequest;

    @Autowired
    public GlobalExceptionHandler(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse =
                createErrorResponse(ex, HttpStatus.BAD_REQUEST, "Validation issues. Check in 'errors' field in the response.");
        errorResponse.setErrors(ex.getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorResponse.ValidationErrors(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
        return createErrorResponseEntity(errorResponse);

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUnhandledException(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return createErrorResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> createErrorResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(Exception ex, HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                httpServletRequest.getRequestURI(),
                LocalDateTime.now());
        if (stackTrace) errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        return errorResponse;
    }

}
