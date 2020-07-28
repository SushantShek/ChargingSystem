package com.charging.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ChargingSessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String sessionNotFoundHandler(ChargingSessionNotFoundException ex) {
        return ex.getMessage();
    }
}
