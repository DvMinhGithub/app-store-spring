package com.mdv.appstore.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mdv.appstore.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(), "Endpoint not found: " + ex.getRequestURL(), null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<String> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {
        return new ApiResponse<>(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "HTTP method not supported: " + ex.getMethod(),
                null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid request body", null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(), "Invalid parameter: " + ex.getName(), null);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleDataAccessException(DataAccessException ex) {
        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database error: " + ex.getMessage(),
                null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleAllExceptions(Exception ex) {
        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred: " + ex.getMessage(),
                null);
    }
}
