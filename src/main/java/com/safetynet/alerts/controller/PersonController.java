package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController (PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<PersonEntity> getPeople() {
        return personService.getPeople();
    }
    @PostMapping()
    public PersonEntity createPerson(@RequestBody PersonEntity person) {
        return null;
    }

    @PutMapping()
    public PersonEntity updatePerson(@RequestBody PersonEntity person) {
        return null;
    }

    @DeleteMapping
    public void deletePerson(@RequestBody PersonDto personDto) {

    }
}
