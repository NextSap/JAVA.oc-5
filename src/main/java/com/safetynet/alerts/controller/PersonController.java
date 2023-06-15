package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
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

    @GetMapping// TODO: à supprimer
    public ResponseEntity<PersonResponse> getPerson(@RequestParam String firstName, String lastName) {
        return new ResponseEntity<>(personService.getPersonResponseByName(firstName, lastName), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest personRequest) {
        return new ResponseEntity<>(personService.createPerson(personRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PersonResponse> updatePerson(@Valid @RequestBody PersonRequest personDto) {
        return new ResponseEntity<>(personService.updatePerson(personDto), HttpStatus.OK);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, String lastName) {
        personService.deletePerson(firstName, lastName);
    }
}
