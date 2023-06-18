package com.safetynet.alerts.service;

import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.exception.PersonException;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.ChildAlertResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .findFirst().orElseThrow(() -> new PersonException.PersonNotFoundException("Person with name `" + firstName + " " + lastName + "` not found"));
    }

    public PersonEntity getPersonEntityById(long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonException.PersonNotFoundException("Person with id `" + id + "` not found"));
    }

    public List<PersonEntity> getPeople() {
        return personRepository.findAll();
    }

    public PersonResponse getPersonResponseByName(String firstName, String lastName) {
        PersonEntity personEntity = personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst().orElseThrow(() -> new PersonException.PersonNotFoundException("Person with name `" + firstName + " " + lastName + "` not found"));
        return personMapper.toPersonResponse(personEntity);
    }

    public PersonResponse createPerson(PersonRequest personRequest) {
        if (checkPersonExists(personRequest.getFirstName(), personRequest.getLastName())) {
            throw new PersonException.PersonAlreadyExistsException("Person with name `" + personRequest.getFirstName() + " " + personRequest.getLastName() + "` already exists");
        }

        PersonEntity personEntity = personRepository.save(personMapper.toPersonEntity(personRequest));
        return personMapper.toPersonResponse(personEntity);
    }

    public PersonResponse updatePerson(PersonRequest personRequest) {
        if (!checkPersonExists(personRequest.getFirstName(), personRequest.getLastName())) {
            throw new PersonException.PersonNotFoundException("Person with name `" + personRequest.getFirstName() + " " + personRequest.getLastName() + "` not found");
        }

        PersonEntity personEntity = getPersonEntityByName(personRequest.getFirstName(), personRequest.getLastName());
        PersonEntity updatedPersonEntity = personRepository.save(personMapper.toPersonEntity(personRequest, personEntity.getId()));

        return personMapper.toPersonResponse(updatedPersonEntity);
    }

    public void deletePerson(String firstName, String lastName) {
        PersonEntity personEntity = getPersonEntityByName(firstName, lastName);
        personRepository.delete(personEntity);
    }

    public ChildAlertResponse getChildAlert(String address) {
        List<PersonEntity> children = getPeople().stream().filter(person -> !DateUtils.getInstance().isMajor(person.getBirthdate()) && person.getAddress().getStreet().equals(address)).toList();
        List<PersonEntity> familyMembers = getPeople().stream().filter(person -> person.getAddress().getStreet().equals(address) && DateUtils.getInstance().isMajor(person.getBirthdate())).toList();

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
        List<String> emails = new ArrayList<>();

        for (PersonEntity person : people) {
            String email = person.getEmail();
            if (!emails.contains(email)) emails.add(person.getEmail());
        }

        return emails;
    }

    public List<PersonEntity> getPeopleFromStreet(String street) {
        return getPeople().stream().filter(person -> person.getAddress().getStreet().equals(street)).toList();
    }

    public List<PersonEntity> getPeopleFromFireStation(FireStationEntity fireStationEntity) {
        return getPeople().stream().filter(person -> fireStationEntity.getAddresses().contains(person.getAddress().getStreet())).toList();
    }

    public boolean checkPersonExists(String firstName, String lastName) {
        return getOptionalPersonEntityByName(firstName, lastName).isPresent();
    }
}
