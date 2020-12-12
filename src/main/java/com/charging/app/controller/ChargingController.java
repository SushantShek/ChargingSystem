package com.charging.app.controller;

import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import com.charging.app.service.ChargingSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ChargingController {


    private final ChargingSessionService chargingSessionService;

    public ChargingController(ChargingSessionService chargingSessionService) {
        this.chargingSessionService = chargingSessionService;
    }

    /**
     * Add new charging session
     * for the station
     *
     * @param stationId UUID
     * @return ChargingSession
     */
    @PostMapping("/chargingSessions")
    ChargingSession newChargingSession(@RequestBody ChargingSession stationId) {
        return chargingSessionService.createChargingSession(stationId);
    }

    /**
     * get all charging sessions
     *
     * @return List of all sessions
     */
    @GetMapping("/chargingSessions")
    List<ChargingSession> getAllSessions() {
        return chargingSessionService.fetchChargingSessions();
    }

    /**
     * Retrieve a summary of submitted charging sessions
     * includes:
     * totalCount – total number of charging session for last minute
     * startedCount – total number of started
     * stoppedCount – total number of stopped
     */
    @GetMapping("/chargingSessions/summary")
    SessionSummary getSessionSummary() {
        return chargingSessionService.getSummary();
    }

    /**
     * Stop charging session
     *
     * @param id UUID
     * @return ChargingSession
     */
    @PutMapping("/chargingSessions/{id}")
    ChargingSession stopChargingSession(@PathVariable UUID id) {
        return chargingSessionService.stopChargingSession(id);
    }
}




