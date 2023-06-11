package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.dto.SimplePersonDto;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController (PersonService personService) {
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        return new ResponseEntity<>(personService.createPerson(personDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) {
        return new ResponseEntity<>(personService.updatePerson(personDto), HttpStatus.OK);
    }

    @DeleteMapping
    public void deletePerson(@RequestBody SimplePersonDto simplePersonDto) {
        personService.deletePerson(simplePersonDto);
    }
}
