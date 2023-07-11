package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.AddressResponse;
import com.safetynet.alerts.object.response.FireResponse;
import com.safetynet.alerts.object.response.HomeResponse;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;
    @Mock
    private PersonService personService;
    @Mock
    private MedicalRecordService medicalRecordService;
    @InjectMocks
    private FireStationService fireStationService;

    private final PersonMapper personMapper = PersonMapper.getInstance();
    private final FireStationMapper fireStationMapper = FireStationMapper.getInstance();

    private FireStationEntity fireStationEntity;
    private PersonEntity personEntity;
    private MedicalRecordEntity medicalRecordEntity;

    @BeforeEach
    public void setUpEach() {
        fireStationEntity = FireStationEntity.builder()
                .id(1)
                .addresses(List.of("street"))
                .station(1)
                .build();

        personEntity = PersonEntity.builder()
                .id(1)
                .firstName("Foo")
                .lastName("Bar")
                .address(AddressEntity.builder().street("street").city("city").zip("zip").build())
                .birthdate(DateUtils.getInstance().getDate("01/01/2000").getTime())
                .email("email")
                .phone("phone")
                .build();

        medicalRecordEntity = MedicalRecordEntity.builder()
                .personId(1)
                .medications(List.of())
                .allergies(List.of())
                .build();

        when(fireStationRepository.findAll()).thenReturn(List.of(fireStationEntity));
    }

    @Test
    public void testGetFireStationEntityByStreet() {
        String street = "street";

        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getAddresses().contains(street)).toList()).thenReturn(List.of(fireStationEntity));

        assertEquals(fireStationEntity, fireStationService.getFireStationEntityByStreet(street));
    }

    @Test
    public void testGetFireStationEntityByStation() {
        int station = 1;

        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station).toList()).thenReturn(List.of(fireStationEntity));

        assertEquals(fireStationEntity, fireStationService.getFireStationEntityByStation(station));
    }

    @Test
    public void testGetOptionalFireStationEntityByStation() {
        int station = 1;

        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station).toList()).thenReturn(List.of(fireStationEntity));

        assertEquals(fireStationEntity, fireStationService.getOptionalFireStationEntityByStation(station).get());
    }

    @Test
    public void testCreateFireStation() {
        FireStationRequest fireStationRequest = FireStationRequest.builder()
                .station(2)
                .addresses(List.of("street"))
                .build();

        when(fireStationRepository.save(any())).thenReturn(fireStationEntity);

        assertEquals(fireStationMapper.toFireStationResponse(fireStationEntity), fireStationService.createFireStation(fireStationRequest));
    }

    @Test
    public void testUpdateFireStation() {
        FireStationRequest fireStationRequest = FireStationRequest.builder()
                .station(1)
                .addresses(List.of("street"))
                .build();

        when(fireStationRepository.save(any())).thenReturn(fireStationEntity);

        assertEquals(fireStationMapper.toFireStationResponse(fireStationEntity), fireStationService.updateFireStation(fireStationRequest));
    }

    @Test
    public void testDeleteFireStation() {
        int station = 1;

        doNothing().when(fireStationRepository).delete(any());

        fireStationService.deleteFireStation(station);

        verify(fireStationRepository, times(1)).delete(any());
    }

    @Test
    public void testGetPeopleAndFireStationFromAddress() {
        String address = "street";

        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getAddresses().contains(address)).toList()).thenReturn(List.of(fireStationEntity));

        when(medicalRecordService.getMedicalRecordEntityByName(any(), any())).thenReturn(medicalRecordEntity);
        when(personService.getPeopleFromStreet(any())).thenReturn(List.of(personEntity));

        assertEquals(FireResponse.builder().people(List.of(personMapper.toPersonWithSimpleMedicalsResponse(personEntity, medicalRecordEntity))).station(1).build(), fireStationService.getPeopleAndFireStationFromAddress(address));
    }

    @Test
    public void testGetPhoneFromPeopleCoveredByFireStation() {
        int station = 1;

        when(fireStationRepository.findAll()).thenReturn(List.of(fireStationEntity));
        when(personService.getPeopleFromFireStation(any())).thenReturn(List.of(personEntity));
        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station).toList()).thenReturn(List.of(fireStationEntity));

        assertEquals(List.of("phone"), fireStationService.getPhoneFromPeopleCoveredByFireStation(station));
    }

    @Test
    public void testGetPeopleCoveredByFireStations() {
        Integer[] stations = {1};

        when(fireStationRepository.findAll()).thenReturn(List.of(fireStationEntity));
        when(personService.getPeopleFromFireStation(any())).thenReturn(List.of(personEntity));
        when(personService.getPeopleFromStreet(any())).thenReturn(List.of(personEntity));
        when(medicalRecordService.getMedicalRecordEntityByName(any(), any())).thenReturn(medicalRecordEntity);
        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> Arrays.stream(stations).toList().contains(fireStation.getStation())).toList()).thenReturn(List.of(fireStationEntity));

        assertEquals(List.of(HomeResponse.builder().people(List.of(personMapper.toPersonWithMedicalsResponse(personEntity, medicalRecordEntity))).address(AddressResponse.builder().street("street").city("city").zip("zip").build()).build()), fireStationService.getPeopleCoveredByFireStations(stations));
    }

    @Test
    public void testGetPeopleAndMedicalRecordFromStreet() {
        String street = "street";

        when(fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getAddresses().contains(street)).toList()).thenReturn(List.of(fireStationEntity));

        when(medicalRecordService.getMedicalRecordEntityByName(any(), any())).thenReturn(medicalRecordEntity);
        when(personService.getPeopleFromStreet(any())).thenReturn(List.of(personEntity));

        assertEquals(Map.of(personEntity, medicalRecordEntity), fireStationService.getPeopleAndMedicalRecordFromStreet(street));
    }

    @Test
    public void testCheckFireStationExists() {
        int station = 1;

        assertTrue(fireStationService.checkFireStationExists(station));
        assertFalse(fireStationService.checkFireStationExists(2));
    }
}
