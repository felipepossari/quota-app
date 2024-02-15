package com.felipepossari.quota.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                               WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildResponse(exception.getErrorReason()));
    }

    private List<ErrorResponse> buildResponse(ErrorReason errorReason) {
        return List.of(ErrorResponse.builder()
                .code(errorReason.getCode())
                .message(errorReason.getMessage())
                .build());
    }
}
