package com.charging.app.exception;

public class ChargingSessionNotFoundException  extends RuntimeException{
    public ChargingSessionNotFoundException(Long id) {
        super("Could not find session " + id);
    }
}
