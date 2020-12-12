package com.charging.app.service;

import com.charging.app.common.StatusEnum;
import com.charging.app.common.Utils;
import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import com.charging.app.exception.ChargingSessionNotFoundException;
import com.charging.app.exception.InvalidInputRequestParamException;
import com.charging.app.repository.ChargingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ChargingSessionService {

    private final ChargingSessionRepository repository;

    /**
     * Parameterized Constructor injection
     *
     * @param repository inject
     */
    ChargingSessionService(ChargingSessionRepository repository) {
        this.repository = repository;
    }

    /**
     * Create new charging session and put it to store
     *
     * @param stationId input
     * @return ChargingSession
     */
    public ChargingSession createChargingSession(ChargingSession stationId) {
        log.info("createChargingSession");
        if (stationId == null) {
            throw new ChargingSessionNotFoundException(null);
        }
        return repository.save(createNewSession(stationId.getStationId()));

    }

    /**
     * Get all the charging sessions from store
     *
     * @return list of ChargingSession
     */
    public List<ChargingSession> fetchChargingSessions() {
        log.info("fetchAllChargingSessions");
        return repository.fetchAll();
    }

    /**
     * Display count of session from last minute
     * In_Progress & Finished
     *
     * @return SessionSummary
     */
    public SessionSummary getSummary() {
        log.info("SessionSummary...");
        return repository.getSummary();
    }

    /**
     * Stops current session for given parameter
     *
     * @param id UUID
     * @return ChargingSession
     */
    public ChargingSession stopChargingSession(UUID id) {
        log.info(" stop the charging session");
        if (id == null) {
            throw new InvalidInputRequestParamException(null);
        }
        ChargingSession response = repository.findById(id);
        if(response == null){
            throw new ChargingSessionNotFoundException(id);
        }
        return repository.save(stopSession(response));
    }

    /**
     * create new charging session for given stationId
     *
     * @param id UUID
     * @return charging session
     */
    private ChargingSession createNewSession(String id) {
        if (id == null || id.equals("")) {
            throw new InvalidInputRequestParamException(id);
        }
        ChargingSession chargingSession = new ChargingSession();

        chargingSession.setId(Utils.getUUID());
        chargingSession.setStationId(id);
        chargingSession.setStartedAt(LocalDateTime.now());
        chargingSession.setStatus(StatusEnum.IN_PROGRESS);

        return chargingSession;
    }

    /**
     * Update the Charging session end time and status
     *
     * @param session ChargingSession
     * @return charging session
     */
    private ChargingSession stopSession(ChargingSession session) {
        session.setStoppedAt(LocalDateTime.now());
        session.setStatus(StatusEnum.FINISHED);

        return session;
    }
}
