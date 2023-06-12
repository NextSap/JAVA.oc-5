package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.entity.FireStationEntity;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper = new PersonMapper();

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private PersonEntity getPersonEntity(String firstName, String lastName) {
        return personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person with name " + firstName + " " + lastName + " not found"));
    }

    private List<PersonEntity> getPeople() {
        return personRepository.findAll();
    }

    public PersonDto getPerson(SimplePersonDto simplePersonDto) {
        PersonEntity personEntity = personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(simplePersonDto.getFirstName()) && person.getLastName().equals(simplePersonDto.getLastName()))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person with name " + simplePersonDto.getFirstName() + " " + simplePersonDto.getLastName() + " not found"));
        return personMapper.toPersonDto(personEntity);
    }

    public PersonDto createPerson(PersonDto personDto) {
        personRepository.save(personMapper.toPersonEntity(personDto));
        return personDto;
    }

    public PersonDto updatePerson(PersonDto personDto) {
        PersonEntity personEntity = getPersonEntity(personDto.getFirstName(), personDto.getLastName());
        personRepository.save(personEntity);
        return personDto;
    }

    public void deletePerson(SimplePersonDto personDto) {
        personRepository.delete(getPersonEntity(personDto.getFirstName(), personDto.getLastName()));
    }

    public ChildAlertDto getChildAlert(String address) {
        List<PersonEntity> children = getPeople().stream().filter(person -> !DateUtils.getInstance().isMajor(person.getBirthdate()) && person.getAddress().getStreet().equals(address)).toList();
        List<PersonEntity> familyMembers = getPeople().stream().filter(person -> children.stream().anyMatch(child -> child.getLastName().equals(person.getLastName()) && !child.getFirstName().equals(person.getFirstName()))).toList();

        return ChildAlertDto.builder()
                .children(personMapper.toChildDtoList(children))
                .familyMembers(personMapper.toSimplePersonDtoList(familyMembers))
                .build();
    }

    public PersonWithMedicalsAndEmailDto getPersonInfo(String firstName, String lastName) {
        PersonEntity personEntity = getPersonEntity(firstName, lastName);
        return personMapper.toPersonWithMedicalsAndEmailDto(personEntity);
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
}
