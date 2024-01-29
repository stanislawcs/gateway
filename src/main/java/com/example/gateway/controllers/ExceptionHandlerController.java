package com.example.gateway.controllers;

import com.example.gateway.exceptions.AccessDeniedException;
import com.example.lib.dto.exceptions.ExceptionResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException of type {} was thrown with message: {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error("ValidationException was thrown with message: {}", e.getMessage());

        ExceptionResponse response = new ExceptionResponse();
        StringBuilder stringBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            stringBuilder.append(fieldName).append(": ").append(message).append(";");
            response.setMessage(stringBuilder.toString());
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception of type: {} was thrown: {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleFeignExceptionUnauthorized(FeignException.Unauthorized e) {
        log.error("FeignException: Unauthorized was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleFeignExceptionForbidden(FeignException.Forbidden e) {
        log.error("FeignException: Forbidden was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleFeignExceptionInternalServerError(FeignException.InternalServerError e) {
        log.error("FeignException: InternalServerError was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
