package com.charging.app.repository;

import com.charging.app.common.StatusEnum;
import com.charging.app.common.Utils;
import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChargingSessionRepositoryTest {

    private ConcurrentHashMap<UUID, ChargingSession> mapStore = (ConcurrentHashMap<UUID, ChargingSession>) Utils.globalMAP;

    @InjectMocks
    ChargingSessionRepository repository;

    @InjectMocks
    SessionSummary summary;

    private ChargingSession session;

    @BeforeEach
    void setUp() {
        session = new ChargingSession();
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        session.setId(id);
        mapStore.put(id, session);
        ChargingSession response = repository.findById(id);
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(id, response.getId());
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();
        session.setId(id);
        session.setStationId("TEST-123");
        mapStore.put(id, session);
        ChargingSession response = repository.save(session);
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("TEST-123", response.getStationId());
    }

    @Test
    void fetchAll() {
        ChargingSession session1 = new ChargingSession();
        session1.setId(UUID.randomUUID());
        session1.setStationId("TEST-123");

        ChargingSession session2 = new ChargingSession();
        session2.setId(UUID.randomUUID());
        session2.setStationId("TEST-987");

        mapStore.put(session1.getId(), session1);
        mapStore.put(session2.getId(), session2);

        ArrayList response = repository.fetchAll();

        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    @Test
    void getSummary() {
        ChargingSession session1 = new ChargingSession();
        session1.setId(UUID.randomUUID());
        session1.setStationId("TEST-123");
        session1.setStatus(StatusEnum.IN_PROGRESS);
        session1.setStartedAt(LocalDateTime.now());
        session1.setStoppedAt(null);
        mapStore.put(session1.getId(), session1);
        summary = repository.getSummary();
        assertNotNull(summary);
        assertTrue(summary.getTotalCount() == 1);
    }
}