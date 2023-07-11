package com.safetynet.alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.object.request.AddressRequest;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.AddressResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    private static final String PERSON_URL = "/person";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private static PersonRequest personRequest;
    private static PersonResponse personResponse;

    @BeforeAll
    public static void setUp() {
        personRequest = PersonRequest.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("Phone")
                .email("Email")
                .birthdate(DateUtils.getInstance().getFormattedDate(new Date()))
                .address(AddressRequest.builder().street("street").city("city").zip("zip").build())
                .build();

        personResponse = PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("Phone")
                .email("Email")
                .birthdate(DateUtils.getInstance().getFormattedDate(new Date()))
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .build();
    }

    @Test
    public void testGetPerson() throws Exception {
        when(personService.getPersonResponseByName(any(), any())).thenReturn(personResponse);

        String responseJson = OBJECT_MAPPER.writeValueAsString(personResponse);

        mockMvc.perform(get(PERSON_URL).param("firstName", "Foo").param("lastName", "Bar"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testCreatePerson() throws Exception {
        when(personService.createPerson(any())).thenReturn(personResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(personRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(personResponse);


        mockMvc.perform(post(PERSON_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        when(personService.updatePerson(any())).thenReturn(personResponse);

        String requestJson = OBJECT_MAPPER.writeValueAsString(personRequest);
        String responseJson = OBJECT_MAPPER.writeValueAsString(personResponse);

        mockMvc.perform(put(PERSON_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(delete(PERSON_URL).param("firstName", "Foo").param("lastName", "Bar"))
                .andExpect(status().isOk());
    }
}
