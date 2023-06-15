package com.safetynet.alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.object.response.*;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfusionControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private FireStationService fireStationService;

    private static PersonResponse personResponse;

    @BeforeAll
    public static void setUp() {
        personResponse = PersonResponse.builder().firstName("Foo").lastName("Bar").build();
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        personResponse.setEmail("email");
        personResponse.setAddress(AddressResponse.builder().street("street").city("city").zip("zip").build());
        personResponse.setBirthdate("01/01/2000");
        personResponse.setMedicalRecord(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build());
        personResponse.setAge(23);

        when(personService.getPersonInfo(any(), any())).thenReturn(personResponse);

        mockMvc.perform(get("/personInfo?firstName=Foo&lastName=Bar"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(personResponse)));
    }

    @Test
    public void testGetCommunityEmail() throws Exception {
        when(personService.getCommunityEmail(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/communityEmail").param("city", "city"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(new ArrayList<>())));
    }

    @Test
    public void testGetPeopleCoveredByFireStation() throws Exception {
        personResponse.setPhone("phone");
        personResponse.setAddress(AddressResponse.builder().street("street").city("city").zip("zip").build());

        PeopleCoveredByFireStationResponse peopleCoveredByFireStationResponse = PeopleCoveredByFireStationResponse.builder()
                .adults(1).children(0)
                .people(List.of(personResponse))
                .build();

        when(fireStationService.getPeopleCoveredByFireStation(anyInt())).thenReturn(peopleCoveredByFireStationResponse);

        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(peopleCoveredByFireStationResponse)));
    }

    @Test
    public void testGetPhoneAlert() throws Exception {
        List<String> phones = List.of("phone1", "phone2");
        when(fireStationService.getPhoneFromPeopleCoveredByFireStation(anyInt())).thenReturn(phones);

        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(phones)));
    }

    @Test
    public void testGetPeopleAndFireStationFromAddress() throws Exception {
        personResponse.setPhone("phone");
        personResponse.setAge(23);
        personResponse.setMedicalRecord(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build());

        FireResponse fireResponse = FireResponse.builder().station(1).people(List.of(personResponse)).build();

        when(fireStationService.getPeopleAndFireStationFromAddress(any())).thenReturn(fireResponse);

        mockMvc.perform(get("/fire").param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(fireResponse)));
    }

    @Test
    public void testGetFloodStations() throws Exception {
        personResponse.setPhone("phone");
        personResponse.setAge(23);
        personResponse.setMedicalRecord(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build());

        HomeResponse homeResponse = HomeResponse.builder()
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .people(List.of(personResponse))
                .build();

        when(fireStationService.getPeopleCoveredByFireStations(any())).thenReturn(List.of(homeResponse));

        mockMvc.perform(get("/flood/stations").param("stations", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(List.of(homeResponse))));
    }

    @Test
    public void testGetChildAlert() throws Exception {
        personResponse.setPhone("phone");
        personResponse.setAge(17);
        personResponse.setMedicalRecord(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build());

        ChildAlertResponse childAlertResponse = ChildAlertResponse.builder()
                .children(List.of(personResponse))
                .familyMembers(List.of(personResponse))
                .build();

        when(personService.getChildAlert(any())).thenReturn(childAlertResponse);

        mockMvc.perform(get("/childAlert").param("address", "address"))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(childAlertResponse)));
    }
}
