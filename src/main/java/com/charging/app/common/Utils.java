package com.charging.app.common;

import com.charging.app.entity.ChargingSession;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

    //Current time provider
    public static LocalDateTime currentTime(){
        return LocalDateTime.now();
    }

    //Generates UUID for each session
    public static UUID getUUID(){
       return UUID.randomUUID();
    }
    //Static Map for storing sessions
    public static Map <UUID, ChargingSession> globalMAP = new ConcurrentHashMap<>();
}
