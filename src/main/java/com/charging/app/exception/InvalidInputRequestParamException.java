package com.charging.app.exception;

public class InvalidInputRequestParamException extends RuntimeException {
    public InvalidInputRequestParamException(String id) {
        super("Request parameter is " + id);
    }
}
