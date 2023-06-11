package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonEntity getPerson(PersonDto personDto) {
        return personRepository.findAll().stream()
                .filter(personEntity -> personEntity.getFirstName().equals(personDto.getFirstName()) && personEntity.getLastName().equals(personDto.getLastName()))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }

    public List<PersonEntity> getPeople() {
        return personRepository.findAll();
    }
}
