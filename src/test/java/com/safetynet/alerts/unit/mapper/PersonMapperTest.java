package com.safetynet.alerts.unit.mapper;

import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.object.request.AddressRequest;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.AddressResponse;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import com.safetynet.alerts.object.response.PeopleCoveredByFireStationResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonMapperTest {

    private final PersonMapper personMapper = PersonMapper.getInstance();
    private static PersonRequest personRequest;
    private static PersonResponse personResponse;
    private static PeopleCoveredByFireStationResponse peopleCoveredByFireStationResponse;
    private static PersonEntity personEntity;
    private static MedicalRecordEntity medicalRecordEntity;

    @BeforeAll
    public static void setUp() {
        personRequest = PersonRequest.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .birthdate("01/01/2000")
                .address(AddressRequest.builder().street("street").city("city").zip("zip").build())
                .build();

        personResponse = PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .birthdate("01/01/2000")
                .age(23)
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .medicalRecord(MedicalRecordResponse.builder().firstName("Foo").lastName("Bar").medications(new ArrayList<>()).allergies(new ArrayList<>()).build())
                .build();

        personEntity = PersonEntity.builder()
                .id(1)
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .birthdate(DateUtils.getInstance().getDate("01/01/2000").getTime())
                .address(AddressEntity.builder().street("street").city("city").zip("zip").build())
                .build();

        medicalRecordEntity = MedicalRecordEntity.builder()
                .personId(1)
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        peopleCoveredByFireStationResponse = PeopleCoveredByFireStationResponse.builder()
                .adults(1)
                .children(0)
                .people(List.of(PersonResponse.builder()
                        .firstName("Foo")
                        .lastName("Bar")
                        .phone("phone")
                        .address(AddressResponse.builder().street("street").city("city").zip("zip").build()).build()))
                .build();
    }

    @Test
    public void testToPersonResponse() {
        assertEquals(PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .age(23)
                .build(), personMapper.toPersonResponse(personEntity));
        assertEquals(personResponse, personMapper.toPersonResponse(personEntity, medicalRecordEntity));
    }

    @Test
    public void testToPersonEntity() {
        assertEquals(PersonEntity.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .birthdate(DateUtils.getInstance().getDate("01/01/2000").getTime())
                .address(AddressEntity.builder().street("street").city("city").zip("zip").build())
                .build(), personMapper.toPersonEntity(personRequest));
        assertEquals(personEntity, personMapper.toPersonEntity(personRequest, 1));
    }

    @Test
    public void testToPersonWithMedicalsAndEmailResponse() {
        assertEquals(PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .email("email")
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .age(23)
                .medicalRecord(MedicalRecordResponse.builder().firstName("Foo").lastName("Bar").medications(new ArrayList<>()).allergies(new ArrayList<>()).build())
                .build(), personMapper.toPersonWithMedicalsAndEmailResponse(personEntity, medicalRecordEntity));
    }

    @Test
    public void testToPersonWithMedicalsResponse() {
        assertEquals(PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .age(23)
                .medicalRecord(MedicalRecordResponse.builder().firstName("Foo").lastName("Bar").medications(new ArrayList<>()).allergies(new ArrayList<>()).build())
                .build(), personMapper.toPersonWithMedicalsResponse(personEntity, medicalRecordEntity));
    }

    @Test
    public void testToPeopleCoveredByFireStationResponse() {
        assertEquals(peopleCoveredByFireStationResponse, personMapper.toPeopleCoveredByFireStationResponse(List.of(personEntity)));
    }

    @Test
    public void testToPersonResponseList() {
        assertEquals(List.of(PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .phone("phone")
                .email("email")
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .age(23)
                .build()), personMapper.toPersonResponseList(List.of(personEntity)));
    }

    @Test
    public void testToPersonWithMedicalsResponseList() {
        Map<PersonEntity, MedicalRecordEntity> personMedicalRecordEntityMap = Map.of(personEntity, medicalRecordEntity);

        assertEquals(List.of(PersonResponse.builder()
                .firstName("Foo")
                .lastName("Bar")
                .address(AddressResponse.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .age(23)
                .medicalRecord(MedicalRecordResponse.builder().firstName("Foo").lastName("Bar").medications(new ArrayList<>()).allergies(new ArrayList<>()).build())
                .build()), personMapper.toPersonWithMedicalsResponseList(personMedicalRecordEntityMap));
    }
}
