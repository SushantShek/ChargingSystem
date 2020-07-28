package com.charging.app.controller;

import com.charging.app.common.StatusEnum;
import com.charging.app.entity.ChargingSession;
import com.charging.app.entity.SessionSummary;
import com.charging.app.service.ChargingSessionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChargingController.class)
class ChargingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ChargingSessionService service;

    @Test
    void newChargingSession() throws Exception {

        ChargingSession cs1 = new ChargingSession();
        cs1.setStationId("ABC-1");
        cs1.setId(UUID.randomUUID());

        given(service.createChargingSession(cs1)).willReturn(cs1);

        mvc.perform(post("/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(cs1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationId", is("ABC-1")));
        verify(service, VerificationModeFactory.times(1)).createChargingSession(cs1);
        reset(service);
    }

    @Test
    void getAllSessions() throws Exception {

        ChargingSession cs1 = new ChargingSession();
        ChargingSession cs2 = new ChargingSession();
        ChargingSession cs3 = new ChargingSession();
        cs1.setStationId("ABC-1");
        cs2.setStationId("ABC-2");
        cs3.setStationId("ABC-3");
        List<ChargingSession> allChargingSession = Arrays.asList(cs1, cs2, cs3);

        given(service.fetchChargingSessions()).willReturn(allChargingSession);

        mvc.perform(get("/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stationId", is(cs1.getStationId())));
        verify(service, VerificationModeFactory.times(1)).fetchChargingSessions();
        reset(service);
    }

    @Test
    void getSessionSummary() throws Exception {
        SessionSummary ss = new SessionSummary();
        ss.setStopCount(1);
        ss.setTotalCount(2);
        ss.setStartCount(1);

        given(service.getSummary()).willReturn(ss);

        mvc.perform(get("/chargingSessions/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("startCount", is(1)))
                .andExpect(jsonPath("stopCount", is(1)))
                .andExpect(jsonPath("totalCount", is(2)));

        verify(service, VerificationModeFactory.times(1)).getSummary();
        reset(service);
    }

    @Test
    void stopChargingSession() throws Exception {
        ChargingSession cs1 = new ChargingSession();
        UUID id = UUID.randomUUID();
        cs1.setId(id);
        cs1.setStationId("ABC-1");
        cs1.setStatus(StatusEnum.FINISHED);

        given(service.stopChargingSession(id)).willReturn(cs1);

        mvc.perform(put("/chargingSessions/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("FINISHED")))
                .andExpect(jsonPath("stationId", is("ABC-1")));

        verify(service, VerificationModeFactory.times(1)).stopChargingSession(id);
        reset(service);
    }

    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}