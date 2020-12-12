package com.charging.app.common;

import com.charging.app.entity.ChargingSession;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    //Static Map for storing sessions
    public static SortedMap<UUID, ChargingSession> globalMAP = Collections.synchronizedSortedMap(new TreeMap<>());

    //Current time provider
    public static LocalDateTime currentTime() {
        return LocalDateTime.now();
    }

    //Generates UUID for each session
    public static UUID getUUID() {
        return UUID.randomUUID();
    }
}
