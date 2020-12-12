package com.charging.app.exception;

import java.util.UUID;

public class ChargingSessionNotFoundException extends RuntimeException {
    public ChargingSessionNotFoundException(UUID id) {
        super("Could not find session " + id);
    }
}
