package com.felipepossari.quota.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger exLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<List<ErrorResponse>> handleInvalidRequestException(InvalidRequestException exception,
                                                                             WebRequest request) {
        var response = exception.getBindingResult().getAllErrors().stream()
                .map(it -> ErrorResponse.builder()
                        .code(it.getCode())
                        .message(it.getDefaultMessage())
                        .build()).toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<List<ErrorResponse>> handleUnknownError(Exception exception, WebRequest request) {
        exLogger.error("Unknown error happened. Ex:", exception);
        var error = List.of(ErrorResponse.builder()
                .code(ErrorReason.INTERNAL_ERROR.getCode())
                .message(ErrorReason.INTERNAL_ERROR.getMessage())
                .build());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
