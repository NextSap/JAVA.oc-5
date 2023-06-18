package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final Logger logger = LogManager.getLogger(PersonController.class);
    private final PersonService personService;

    @Autowired
    public PersonController (PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<PersonResponse> getPerson(@RequestParam String firstName, String lastName) {
        PersonResponse personResponse = personService.getPersonResponseByName(firstName, lastName);
        logger.info("Successful Request GET /person?firstName=" + firstName + "&lastName=" + lastName);
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest personRequest) {
        PersonResponse personResponse = personService.getPersonResponseByName(personRequest.getFirstName(), personRequest.getLastName());
        logger.info("Successful Request POST /person");
        return new ResponseEntity<>(personResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PersonResponse> updatePerson(@Valid @RequestBody PersonRequest personRequest) {
        PersonResponse personResponse = personService.getPersonResponseByName(personRequest.getFirstName(), personRequest.getLastName());
        logger.info("Successful Request PUT /person");
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, String lastName) {
        personService.deletePerson(firstName, lastName);
        logger.info("Successful Request DELETE /person?firstName=" + firstName + "&lastName=" + lastName);
    }
}
