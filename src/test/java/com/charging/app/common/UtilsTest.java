package com.charging.app.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testCurrentTime() {
      assertTrue(Utils.currentTime() instanceof LocalDateTime);
    }

    @Test
    void testGetUUID() {
        assertTrue(Utils.getUUID() instanceof UUID);
    }
}