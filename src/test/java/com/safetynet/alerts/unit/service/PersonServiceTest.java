package com.safetynet.alerts.unit.service;

import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.object.request.AddressRequest;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.ChildAlertResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

    private final String firstName = "Foo";
    private final String lastName = "Bar";
    @Mock
    private static PersonRepository personRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private MedicalRecordService medicalRecordService;
    @InjectMocks
    private PersonService personService;
    private final PersonMapper personMapper = PersonMapper.getInstance();
    private static PersonEntity personEntity;

    @BeforeEach
    public void setUpEach() {
        personEntity = PersonEntity.builder()
                .id(1)
                .firstName(firstName)
                .lastName(lastName)
                .address(AddressEntity.builder().street("street").city("city").zip("zip").build())
                .birthdate(DateUtils.getInstance().getDate("01/01/2000").getTime())
                .email("email")
                .phone("phone")
                .build();


        when(personRepository.findAll()).thenReturn(List.of(personEntity));
    }

    @Test
    public void testGetPeople() {
        List<PersonEntity> people = personService.getPeople();

        assertEquals(List.of(personEntity), people);
    }

    @Test
    public void testGetOptionalPersonEntityByName() {
        Optional<PersonEntity> optionalPersonEntity = personService.getOptionalPersonEntityByName(firstName, lastName);

        assertEquals(Optional.of(personEntity), optionalPersonEntity);
    }

    @Test
    public void testGetPersonEntityByName() {PersonEntity personEntity = personService.getPersonEntityByName(firstName, lastName);

        assertEquals(personEntity, personEntity);
    }

    @Test
    public void testGetPersonEntityById() {
        long id = 1;

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(personEntity));

        PersonEntity personEntity = personService.getPersonEntityById(id);

        assertEquals(personEntity, personEntity);
    }

    @Test
    public void testGetPersonResponseByName() {PersonResponse personResponse = personService.getPersonResponseByName(firstName, lastName);

        assertEquals(personMapper.toPersonResponse(personEntity), personResponse);
    }

    @Test
    public void testCreatePerson() {
        String firstName = "Foo";
        String lastName = "Baz";

        when(personRepository.findAll().stream()
                        .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).collect(Collectors.toList())).thenReturn(List.of(personEntity));

        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

        PersonResponse personResponse = personService.createPerson(PersonRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(AddressRequest.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .email("email")
                .phone("phone")
                .build());

        assertEquals(personMapper.toPersonResponse(personEntity), personResponse);
    }

    @Test
    public void testUpdatePerson() {
        PersonRequest personRequest = PersonRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(AddressRequest.builder().street("street").city("city").zip("zip").build())
                .birthdate("01/01/2000")
                .email("email")
                .phone("phone")
                .build();

        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

        PersonResponse personResponse = personService.updatePerson(personRequest);

        assertEquals(personMapper.toPersonResponse(personEntity), personResponse);
    }

    @Test
    public void testDeletePerson() {
        doNothing().when(personRepository).delete(any(PersonEntity.class));

        personService.deletePerson(firstName, lastName);

        verify(personRepository, times(1)).delete(any(PersonEntity.class));
    }

    @Test
    public void testGetChildAlert() {
        personEntity.setBirthdate(new Date().getTime());

        when(personRepository.findAll().stream().filter(person -> !DateUtils.getInstance().isMajor(person.getBirthdate()) && person.getAddress().getStreet().equals("street")).toList()).thenReturn(List.of(personEntity));

        ChildAlertResponse childAlertResponse = personService.getChildAlert("street");

        verify(personRepository, times(2)).findAll();

        assertEquals(ChildAlertResponse.builder()
                .children(List.of(personMapper.toPersonResponse(personEntity)))
                .familyMembers(List.of())
                .build(), childAlertResponse);
    }

    @Test
    public void testGetPersonInfo() {
        MedicalRecordEntity medicalRecordEntity = MedicalRecordEntity.builder().personId(1).medications(new ArrayList<>()).allergies(new ArrayList<>()).build();

        when(medicalRecordRepository.findAll()).thenReturn(List.of(medicalRecordEntity));
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(medicalRecordService.getMedicalRecordEntityByName("Foo", "Bar")).thenReturn(medicalRecordEntity);


        PersonResponse personResponse = personService.getPersonInfo(firstName, lastName);

        verify(personRepository, times(1)).findAll();

        assertEquals(personMapper.toPersonWithMedicalsAndEmailResponse(personEntity, medicalRecordEntity), personResponse);
    }

    @Test
    public void testGetCommunityEmail() {
        String city = "city";

        when(personRepository.findAll().stream().filter(person -> person.getAddress().getCity().equals(city)).toList()).thenReturn(List.of(personEntity));

        List<String> emails = personService.getCommunityEmail(city);

        verify(personRepository, times(1)).findAll();

        assertEquals(List.of(personEntity.getEmail()), emails);
    }

    @Test
    public void testGetPeopleFromStreet() {
        String street = "street";

        when(personRepository.findAll().stream().filter(person -> person.getAddress().getStreet().equals(street)).toList()).thenReturn(List.of(personEntity));

        List<PersonEntity> people = personService.getPeopleFromStreet(street);

        verify(personRepository, times(1)).findAll();

        assertEquals(List.of(personEntity), people);
    }

    @Test
    public void testGetPeopleFromFireStation() {
        FireStationEntity fireStationEntity = FireStationEntity.builder().station(1).addresses(List.of("street")).build();

        when(personRepository.findAll().stream().filter(person -> fireStationEntity.getAddresses().contains(person.getAddress().getStreet())).toList()).thenReturn(List.of(personEntity));

        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);

        verify(personRepository, times(1)).findAll();

        assertEquals(List.of(personEntity), people);
    }

    @Test
    public void testCheckPersonExists() {
        assertTrue(personService.checkPersonExists(firstName, lastName));
        assertFalse(personService.checkPersonExists("Foo", "Baz"));
    }
}
