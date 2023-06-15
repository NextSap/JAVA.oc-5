package com.safetynet.alerts.unit.mapper;

import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireResponse;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationMapperTest {

    private final FireStationMapper fireStationMapper = FireStationMapper.getInstance();

    private static FireStationRequest fireStationRequest;
    private static FireStationEntity fireStationEntity;
    private static FireStationResponse fireStationResponse;

    @BeforeAll
    public static void setUp() {
        fireStationRequest = FireStationRequest.builder()
                .addresses(List.of("address"))
                .station(1)
                .build();

        fireStationEntity = FireStationEntity.builder()
                .id(1)
                .addresses(List.of("address"))
                .station(1)
                .build();

        fireStationResponse = FireStationResponse.builder()
                .addresses(List.of("address"))
                .station(1)
                .build();
    }

    @Test
    public void toFireStationResponseTest() {
        assertEquals(fireStationResponse, fireStationMapper.toFireStationResponse(fireStationEntity));
    }

    @Test
    public void toFireStationEntityTest() {
        assertEquals(FireStationEntity.builder().station(1).addresses(List.of("address")).build(),
                fireStationMapper.toFireStationEntity(fireStationRequest));
    }

    @Test
    public void toFireStationEntityWithIdTest() {
        assertEquals(fireStationEntity, fireStationMapper.toFireStationEntity(fireStationRequest, 1));
    }

    @Test
    public void toFireResponseTest() {
        PersonResponse personResponse = PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .age(23)
                .medicalRecord(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build()).build();
        assertEquals(FireResponse.builder().station(1).people(List.of(personResponse)).build(), fireStationMapper.toFireResponse(1, List.of(personResponse)));
    }
}
