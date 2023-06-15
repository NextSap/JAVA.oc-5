package com.safetynet.alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import com.safetynet.alerts.service.MedicalRecordService;
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
public class MedicalRecordControllerTest {
    private static final String MEDICAL_RECORD_URL = "/medicalRecord";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private static MedicalRecordRequest medicalRecordRequest;
    private static MedicalRecordResponse medicalRecordResponse;

    @BeforeAll
    public static void setUp() {
        medicalRecordRequest = MedicalRecordRequest.builder()
                .firstName("Foo")
                .lastName("Bar")
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        medicalRecordResponse = MedicalRecordResponse.builder()
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
    }

    @Test
    public void testGetMedicalRecord() throws Exception {
        when(medicalRecordService.getMedicalRecord(any(), any())).thenReturn(medicalRecordResponse);

        String json = OBJECT_MAPPER.writeValueAsString(medicalRecordResponse);

        mockMvc.perform(get(MEDICAL_RECORD_URL).param("firstName", "Foo").param("lastName", "Bar"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void testCreateMedicalRecord() throws Exception {
        when(medicalRecordService.createMedicalRecord(any())).thenReturn(medicalRecordResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(medicalRecordRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(medicalRecordResponse);

        mockMvc.perform(post(MEDICAL_RECORD_URL).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(medicalRecordResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(medicalRecordRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(medicalRecordResponse);

        mockMvc.perform(put(MEDICAL_RECORD_URL).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete(MEDICAL_RECORD_URL).param("firstName", "Foo").param("lastName", "Bar"))
                .andExpect(status().isOk());
    }
}
