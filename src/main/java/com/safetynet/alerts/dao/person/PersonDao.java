package com.safetynet.alerts.dao.person;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    List<Person> findAll();
    Optional<Person> findByName(PersonDto personDto);
    Person save(Person person);
    Person update(Person person);
    void delete(PersonDto personDto);
}
