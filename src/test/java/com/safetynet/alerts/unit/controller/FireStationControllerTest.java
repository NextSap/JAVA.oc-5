package com.safetynet.alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    private static final String FIRE_STATION_URL = "/firestation";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private static FireStationRequest fireStationRequest;
    private static FireStationResponse fireStationResponse;
    private static int station;

    @BeforeAll
    public static void setUp() {
        fireStationRequest = FireStationRequest.builder().station(1).addresses(new ArrayList<>()).build();
        fireStationResponse = FireStationResponse.builder().station(1).addresses(new ArrayList<>()).build();
        station = 1;
    }

    @Test
    public void testCreateFireStation() throws Exception {
        when(fireStationService.createFireStation(any())).thenReturn(fireStationResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(fireStationRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(fireStationResponse);

        mockMvc.perform(post(FIRE_STATION_URL).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testUpdateFireStation() throws Exception {
        when(fireStationService.updateFireStation(any())).thenReturn(fireStationResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(fireStationRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(fireStationResponse);

        mockMvc.perform(put(FIRE_STATION_URL).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        String json = OBJECT_MAPPER.writeValueAsString(station);

        mockMvc.perform(delete(FIRE_STATION_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
}
