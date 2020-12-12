package com.charging.app.service;

import com.charging.app.common.StatusEnum;
import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import com.charging.app.exception.ChargingSessionNotFoundException;
import com.charging.app.exception.InvalidInputRequestParamException;
import com.charging.app.repository.ChargingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChargingSessionServiceTest {

    @Mock
    private ChargingSessionRepository repository;
    @InjectMocks
    private ChargingSessionService service;

    private ChargingSession chargingSession;

    @BeforeEach
    void setUp() {
        chargingSession = new ChargingSession();
        chargingSession.setId(UUID.randomUUID());
        chargingSession.setStartedAt(LocalDateTime.now());
        chargingSession.setStatus(StatusEnum.IN_PROGRESS);
        chargingSession.setStationId("abc-12345");
    }

    @Test
    void createChargingSession_withValidObject() {
        when(repository.save(any(ChargingSession.class))).thenReturn(chargingSession);
        ChargingSession session = new ChargingSession();
        session.setStationId("1");
        session.setId(UUID.randomUUID());
        ChargingSession response = service.createChargingSession(session);
        assertNotNull(response);
        assertEquals("abc-12345", response.getStationId());
        assertNotNull(response.getId());
    }

    @Test
    void createChargingSession_withNullObject() {
        assertThrows(ChargingSessionNotFoundException.class, () -> service.createChargingSession(null));
    }

    @Test
    void fetchChargingSessions_withValuesAsResponse() {
        ArrayList<ChargingSession> listArray = new ArrayList<>(Arrays.asList(new ChargingSession(), new ChargingSession()));
        when(repository.fetchAll()).thenReturn(listArray);
        ArrayList response = (ArrayList) service.fetchChargingSessions();

        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    @Test
    void fetchChargingSessions_withEmptyResponse() {
        ArrayList<ChargingSession> listArray = new ArrayList<>();
        when(repository.fetchAll()).thenReturn((listArray));
        ArrayList response = (ArrayList) service.fetchChargingSessions();
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getSummary_withSummaryData() {
        SessionSummary summary = new SessionSummary();
        summary.setStartCount(3);
        summary.setTotalCount(6);
        summary.setStopCount(3);
        when(repository.getSummary()).thenReturn(summary);
        SessionSummary response = service.getSummary();
        assertNotNull(response);
        assertEquals(3, response.getStartCount());
        assertEquals(3, response.getStopCount());
        assertEquals(6, response.getTotalCount());
    }

    @Test
    void stopChargingSession() {
        chargingSession.setStoppedAt(LocalDateTime.now());
        chargingSession.setStatus(StatusEnum.FINISHED);
        when(repository.findById(any())).thenReturn(chargingSession);
        when(repository.save(any())).thenReturn(chargingSession);
        ChargingSession response = service.stopChargingSession(UUID.randomUUID());
        assertNotNull(response);
        assertEquals(StatusEnum.FINISHED, response.getStatus());
    }

    @Test
    void stopChargingSession_withNullUUID() {
        Exception exception = assertThrows(InvalidInputRequestParamException.class, () -> {
            service.stopChargingSession(null);
        });
        assertTrue(exception.getMessage().contains("Request parameter is null"));
    }
}