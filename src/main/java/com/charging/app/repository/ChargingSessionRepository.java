package com.charging.app.repository;

import com.charging.app.common.StatusEnum;
import com.charging.app.common.Utils;
import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ChargingSessionRepository {

    /**
     * Concurrent HashMap to store the sessions
     */
    private static final Map<UUID, ChargingSession> repo = Utils.globalMAP;


    /**
     * finds a session by UUID
     *
     * @param id UUID
     * @return ChargingSession
     */
    public ChargingSession findById(UUID id) {
        log.info("findById : " + repo.get(id));

        return repo.get(id);
    }

    /**
     * Save the session in Map
     *
     * @param chargingSession as input
     * @return saved ChargingSession
     */
    public ChargingSession save(ChargingSession chargingSession) {
        log.info("SAVE : UUID " + chargingSession.getId());
        repo.put(chargingSession.getId(), chargingSession);

        return chargingSession;
    }

    /**
     * return all the available session in store
     *
     * @return list of ChargingSession
     */
    public ArrayList fetchAll() {
        return new ArrayList(repo.values());
    }

    /**
     * Summary of last minute sessions
     *
     * @return count of all lastminute session
     * count started sessions in last minute
     * count all finished session
     */
    public SessionSummary getSummary() {
        log.info("getSummary ");
        SessionSummary sessionSummary = new SessionSummary();
        LocalDateTime time = Utils.currentTime().minusMinutes(1);
        List<ChargingSession> lastMinuteSession = repo.values().stream()
                .filter(s -> s.getStartedAt().isAfter(time) || (null != s.getStoppedAt() && s.getStoppedAt().isAfter(time)))
                .collect(Collectors.toList());

        long startedLastMinute = lastMinuteSession.stream()
                .filter(s -> s.getStatus().equals(StatusEnum.IN_PROGRESS)).count();

        sessionSummary.setTotalCount(lastMinuteSession.size());
        sessionSummary.setStartCount((int) startedLastMinute);
        sessionSummary.setStopCount(lastMinuteSession.size() - (int) startedLastMinute);

        return sessionSummary;
    }
}
