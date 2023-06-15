package com.safetynet.alerts.unit.mapper;

import com.safetynet.alerts.mapper.MedicalRecordMapper;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordMapperTest {

    private final MedicalRecordMapper medicalRecordMapper = MedicalRecordMapper.getInstance();
    private static MedicalRecordResponse medicalRecordResponse;
    private static MedicalRecordEntity medicalRecordEntity;
    private static MedicalRecordRequest medicalRecordRequest;

    @BeforeAll
    public static void setUp() {
        medicalRecordResponse = MedicalRecordResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        medicalRecordEntity = MedicalRecordEntity.builder()
                .personId(1)
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
        medicalRecordRequest = MedicalRecordRequest.builder()
                .firstName("Foo")
                .lastName("Bar")
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
    }

    @Test
    public void toMedicalRecordResponseTest() {
        assertEquals(medicalRecordResponse, medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity, "Foo", "Bar"));
    }

    @Test
    public void toMedicalRecordEntityTest() {
        assertEquals(MedicalRecordEntity.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build(), medicalRecordMapper.toMedicalRecordEntity(medicalRecordRequest));
    }

    @Test
    public void toMedicalRecordEntityWithIdTest() {
        assertEquals(medicalRecordEntity, medicalRecordMapper.toMedicalRecordEntity(medicalRecordRequest, 1));
    }

    @Test
    public void toSimpleMedicalRecordResponseTest() {
        assertEquals(MedicalRecordResponse.builder().medications(new ArrayList<>()).allergies(new ArrayList<>()).build(), medicalRecordMapper.toSimpleMedicalRecordResponse(medicalRecordEntity));
    }
}
