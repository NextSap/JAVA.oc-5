package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfusionController {

    private final PersonService personService;

    @Autowired
    public ConfusionController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<PeopleCoveredByFireStationDto> getPeopleCoveredByFireStation(@RequestParam("stationNumber") int stationNumber) {
        return null;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDto> getChildrenFromAddress(@RequestParam("address") String address) {
        return new ResponseEntity<>(personService.getChildAlert(address), HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneFromPeopleCoveredByFireStation(@RequestParam("firestation") int firestation) {
        return null;
    }

    @GetMapping("/fire")
    public ResponseEntity<FireDto> getPeopleAndFireStationFromAddress(@RequestParam("address") String address) {
        return null;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<HomeDto>> getPeopleCoveredByFireStations(@RequestParam("stations") List<Integer> stations) {
        return null;
    }

    @GetMapping("/personInfo")
    public ResponseEntity<PersonWithMedicalsAndEmailDto> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return new ResponseEntity<>(personService.getPersonInfo(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") String city) {
        return new ResponseEntity<>(personService.getCommunityEmail(city), HttpStatus.OK);
    }
}
