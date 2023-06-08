package com.safetynet.alerts.dao.person;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {
    @Override
    public List<Person> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Person> findByName(PersonDto personDto) {
        return Optional.empty();
    }

    @Override
    public Person save(Person person) {
        return null;
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public void delete(PersonDto personDto) {

    }
}
