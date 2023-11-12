package com.sap.cloud.cities.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = CityController.class)
public class CityControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleTrainingSessionNotCompletedException(IllegalArgumentException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }


    private ResponseEntity<String> handleException(Exception e, HttpStatus status) {
        log.error("Exception: ", e);
        return new ResponseEntity<>(e.getMessage(), status);
    }
}
