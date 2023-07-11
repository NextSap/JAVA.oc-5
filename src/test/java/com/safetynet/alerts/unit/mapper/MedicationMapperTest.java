package com.safetynet.alerts.unit.mapper;

import com.safetynet.alerts.mapper.MedicationMapper;
import com.safetynet.alerts.object.entity.MedicationEntity;
import com.safetynet.alerts.object.request.MedicationRequest;
import com.safetynet.alerts.object.response.MedicationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicationMapperTest {

    private final MedicationMapper medicationMapper = MedicationMapper.getInstance();
    private static MedicationRequest medicationRequest;
    private static MedicationEntity medicationEntity;
    private static MedicationResponse medicationResponse;

    @BeforeAll
    public static void setUp() {
        medicationRequest = MedicationRequest.builder()
                .name("name")
                .mlDosage(1L)
                .build();

        medicationEntity = MedicationEntity.builder()
                .id(1)
                .name("name")
                .mlDosage(1L)
                .build();

        medicationResponse = MedicationResponse.builder()
                .name("name")
                .mlDosage(1L)
                .build();
    }

    @Test
    public void toMedicationResponseTest() {
        assertEquals(medicationResponse, medicationMapper.toMedicationResponse(medicationEntity));
    }

    @Test
    public void toMedicationEntityTest() {
        assertEquals(MedicationEntity.builder().name("name").mlDosage(1L).build(), medicationMapper.toMedicationEntity(medicationRequest));
    }
}
