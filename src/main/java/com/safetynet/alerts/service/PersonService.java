package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.dto.SimplePersonDto;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }

    public PersonDto getPerson(SimplePersonDto simplePersonDto) {
        PersonEntity personEntity = personRepository.findAll().stream()
                .filter(person -> person.getFirstName().equals(simplePersonDto.getFirstName()) && person.getLastName().equals(simplePersonDto.getLastName()))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found"));
        return personMapper.toPersonDto(personEntity);
    }

    public PersonDto createPerson(PersonDto personDto) {
        personRepository.save(personMapper.toPersonEntity(personDto));
        return personDto;
    }

    public PersonDto updatePerson(PersonDto personDto) {
        PersonEntity personEntity = getPersonEntity(personDto.getFirstName(), personDto.getLastName());
        personRepository.save(personEntity);
        return personMapper.toPersonDto(personEntity);
    }

    public void deletePerson(SimplePersonDto personDto) {
        personRepository.delete(getPersonEntity(personDto.getFirstName(), personDto.getLastName()));
    }
}
