package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.response.*;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ConfusionController {

    private final Logger logger = LogManager.getLogger(ConfusionController.class);

    private final PersonService personService;
    private final FireStationService fireStationService;

    @Autowired
    public ConfusionController(PersonService personService, FireStationService fireStationService) {
        this.personService = personService;
        this.fireStationService = fireStationService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<PeopleCoveredByFireStationResponse> getPeopleCoveredByFireStation(@Valid  @RequestParam("stationNumber") int stationNumber) {
        PeopleCoveredByFireStationResponse peopleCoveredByFireStationResponse = fireStationService.getPeopleCoveredByFireStation(stationNumber);
        logger.info("Successful Request GET /firestation?stationNumber=" + stationNumber);
        return new ResponseEntity<>(peopleCoveredByFireStationResponse, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildrenFromAddress(@Valid @RequestParam("address") String address) {
        ChildAlertResponse childAlertResponse = personService.getChildAlert(address);
        logger.info("Successful Request GET /childAlert?address=" + address);
        return new ResponseEntity<>(childAlertResponse, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneFromPeopleCoveredByFireStation(@Valid @RequestParam("firestation") int firestation) {
        List<String> phoneList = fireStationService.getPhoneFromPeopleCoveredByFireStation(firestation);
        logger.info("Successful Request GET /phoneAlert?firestation=" + firestation);
        return new ResponseEntity<>(phoneList, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<FireResponse> getPeopleAndFireStationFromAddress(@Valid @RequestParam("address") String address) {
        FireResponse fireResponse = fireStationService.getPeopleAndFireStationFromAddress(address);
        logger.info("Successful Request GET /fire?address=" + address);
        return new ResponseEntity<>(fireResponse, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<HomeResponse>> getPeopleCoveredByFireStations(@Valid @RequestParam("stations") Integer[] stations) {
        List<HomeResponse> homeResponseList = fireStationService.getPeopleCoveredByFireStations(stations);
        logger.info("Successful Request GET /flood/stations?stations=" + Arrays.toString(stations));
        return new ResponseEntity<>(homeResponseList, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<PersonResponse> getPersonInfo(@Valid @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        PersonResponse personResponse = personService.getPersonInfo(firstName, lastName);
        logger.info("Successful Request GET /personInfo?firstName=" + firstName + "&lastName=" + lastName);
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@Valid @RequestParam("city") String city) {
        List<String> emailList = personService.getCommunityEmail(city);
        logger.info("Successful Request GET /communityEmail?city=" + city);
        return new ResponseEntity<>(emailList, HttpStatus.OK);
    }
}
