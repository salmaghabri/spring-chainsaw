package com.cat.util.handler;


import com.cat.util.exception.AbstractAppException;
import com.cat.util.exception.BadRequestException;
import com.cat.util.exception.model.ErrorResponse;
import com.cat.util.tools.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(AbstractAppException.class)
    public ResponseEntity<ErrorResponse> appExceptionHandler(AbstractAppException exception) {
        return null;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> backendExceptionHandler(BadRequestException badRequestException) {
        return new ResponseEntity<>(createCommonErrors(
                HttpStatus.BAD_REQUEST.value(),
                badRequestException.getMainResource()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericExceptionHandler() {
        return new ResponseEntity<>(createCommonErrors(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Constants.UNKNOWN),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ErrorResponse createCommonErrors(Integer httpStatus, String message) {
        return ErrorResponse
                .builder()
                .httpStatus(httpStatus)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
