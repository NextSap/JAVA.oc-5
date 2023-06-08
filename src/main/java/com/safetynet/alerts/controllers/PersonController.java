package com.safetynet.alerts.controllers;

import com.safetynet.alerts.dao.person.PersonDaoImpl;
import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonDaoImpl personDao = new PersonDaoImpl();

    @PostMapping()
    public Person createPerson(@RequestBody Person person) {
        return personDao.save(person);
    }

    @PutMapping()
    public Person updatePerson(@RequestBody Person person) {
        return personDao.update(person);
    }

    @DeleteMapping
    public void deletePerson(@RequestBody PersonDto personDto) {
        personDao.delete(personDto);
    }
}
