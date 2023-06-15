package com.safetynet.alerts.service;

import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.exception.PersonAlreadyExistException;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.ChildAlertResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordService medicalRecordService;
    private final PersonMapper personMapper = PersonMapper.getInstance();

    @Autowired
    public PersonService(PersonRepository personRepository, @Lazy MedicalRecordService medicalRecordService) {
        this.personRepository = personRepository;
        this.medicalRecordService = medicalRecordService;
    }

    public Optional<PersonEntity> getOptionalPersonEntityByName(String firstName, String lastName) {
        return personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    public PersonEntity getPersonEntityByName(String firstName, String lastName) {
        return personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person with name " + firstName + " " + lastName + " not found"));
    }

    public PersonEntity getPersonEntityById(long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found"));
    }

    private List<PersonEntity> getPeople() {
        return personRepository.findAll();
    }

    public PersonResponse getPersonResponseByName(String firstName, String lastName) {
        PersonEntity personEntity = personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person with name " + firstName + " " + lastName + " not found"));
        return personMapper.toPersonResponse(personEntity);
    }

    public PersonResponse createPerson(PersonRequest personDto) {
        checkPersonExists(personDto.getFirstName(), personDto.getLastName());

        PersonEntity personEntity = personRepository.save(personMapper.toPersonEntity(personDto));
        return personMapper.toPersonResponse(personEntity);
    }

    public PersonResponse updatePerson(PersonRequest personRequest) {
        PersonEntity personEntity = getPersonEntityByName(personRequest.getFirstName(), personRequest.getLastName());
        PersonEntity updatedPersonDto = personRepository.save(personMapper.toPersonEntity(personRequest, personEntity.getId()));

        return personMapper.toPersonResponse(updatedPersonDto);
    }

    public void deletePerson(String firstName, String lastName) {
        PersonEntity personEntity = getPersonEntityByName(firstName, lastName);
        personRepository.delete(personEntity);
    }

    public ChildAlertResponse getChildAlert(String address) {
        List<PersonEntity> children = getPeople().stream().filter(person -> !DateUtils.getInstance().isMajor(person.getBirthdate()) && person.getAddress().getStreet().equals(address)).toList();
        List<PersonEntity> familyMembers = getPeople().stream().filter(person -> children.stream().anyMatch(child -> child.getLastName().equals(person.getLastName()) && !child.getFirstName().equals(person.getFirstName()))).toList();

        return ChildAlertResponse.builder()
                .children(personMapper.toPersonResponseList(children))
                .familyMembers(personMapper.toPersonResponseList(familyMembers))
                .build();
    }

    public PersonResponse getPersonInfo(String firstName, String lastName) {
        PersonEntity personEntity = getPersonEntityByName(firstName, lastName);
        MedicalRecordEntity medicalRecordEntity = medicalRecordService.getMedicalRecordEntityByName(firstName, lastName);

        return personMapper.toPersonWithMedicalsAndEmailResponse(personEntity, medicalRecordEntity);
    }

    public List<String> getCommunityEmail(String city) {
        List<PersonEntity> people = getPeople().stream().filter(person -> person.getAddress().getCity().equals(city)).toList();
        return people.stream().map(PersonEntity::getEmail).toList();
    }

    public List<PersonEntity> getPeopleFromStreet(String street) {
        return getPeople().stream().filter(person -> person.getAddress().getStreet().equals(street)).toList();
    }

    public List<PersonEntity> getPeopleFromFireStation(FireStationEntity fireStationEntity) {
        return getPeople().stream().filter(person -> fireStationEntity.getAddresses().contains(person.getAddress().getStreet())).toList();
    }

    private void checkPersonExists(String firstName, String lastName) {
        if (getOptionalPersonEntityByName(firstName, lastName).isPresent()) {
            throw new PersonAlreadyExistException("Person with name " + firstName + " " + lastName + " already exists");
        }
    }
}
