package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.exception.MedicalException;
import com.safetynet.alerts.mapper.MedicalRecordMapper;
import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicalRecordServiceTest {

    private final String firstName = "Foo";
    private final String lastName = "Bar";
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private PersonService personService;
    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private final MedicalRecordMapper medicalRecordMapper = MedicalRecordMapper.getInstance();

    private MedicalRecordEntity medicalRecordEntity;
    private PersonEntity personEntity;

    @BeforeEach
    public void setUpEach() {
        medicalRecordEntity = MedicalRecordEntity.builder()
                .personId(1)
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        personEntity = PersonEntity.builder()
                .id(1)
                .firstName(firstName)
                .lastName(lastName)
                .address(AddressEntity.builder().street("street").city("city").zip("zip").build())
                .birthdate(DateUtils.getInstance().getDate("01/01/2000").getTime())
                .email("email")
                .phone("phone")
                .build();

        when(medicalRecordRepository.findAll()).thenReturn(List.of(medicalRecordEntity));
        when(personService.getPersonEntityById(anyLong())).thenReturn(personEntity);
        when(personService.getPersonEntityByName(anyString(), anyString())).thenReturn(personEntity);
    }

    @Test
    public void testGetMedicalRecordEntityByName() {
        when(medicalRecordRepository.findAll().stream().filter(medicalRecordEntity -> personEntity.getFirstName().equals(firstName) && personEntity.getLastName().equals(lastName)).toList())
                .thenReturn(List.of(medicalRecordEntity));

        assertEquals(medicalRecordEntity, medicalRecordService.getMedicalRecordEntityByName(firstName, lastName));
    }

    @Test
    public void testGetOptionalMedicalRecordEntityByName() {
        when(medicalRecordRepository.findAll().stream().filter(medicalRecordEntity -> personEntity.getFirstName().equals(firstName) && personEntity.getLastName().equals(lastName)).toList())
                .thenReturn(List.of(medicalRecordEntity));

        assertEquals(Optional.of(medicalRecordEntity), medicalRecordService.getOptionalMedicalRecordEntityByName(firstName, lastName));
    }

    @Test
    public void testGetMedicalRecord() {
        assertEquals(medicalRecordMapper.toSimpleMedicalRecordResponse(medicalRecordEntity), medicalRecordService.getMedicalRecord(firstName, lastName));
    }

    @Test
    public void testCreateMedicalRecord() {
        when(medicalRecordRepository.save(any())).thenReturn(medicalRecordEntity);

        MedicalRecordRequest medicalRecordRequest = MedicalRecordRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        assertEquals(medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity, firstName, lastName), medicalRecordService.createMedicalRecord(medicalRecordRequest));
    }

    @Test
    public void testUpdateMedicalRecord() {
        when(medicalRecordRepository.save(any())).thenReturn(medicalRecordEntity);

        MedicalRecordRequest medicalRecordRequest = MedicalRecordRequest.builder()
                .firstName(firstName)
                .lastName("Baz")
                .medications(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        assertEquals(medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity, firstName, "Baz"), medicalRecordService.updateMedicalRecord(medicalRecordRequest));
    }

    @Test
    public void testDeleteMedicalRecord() {
        doNothing().when(medicalRecordRepository).delete(any());

        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        verify(medicalRecordRepository, times(1)).delete(any());
    }

    @Test
    public void testMedicalRecordExists() {
        assertThrows(MedicalException.MedicalAlreadyExistsException.class, () -> medicalRecordService.checkMedicalRecordExists(firstName, lastName));
        assertFalse(medicalRecordService.checkMedicalRecordExists(firstName, "Baz"));
    }
}
