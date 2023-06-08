package com.safetynet.alerts.controllers;

import com.safetynet.alerts.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfusionController {

    @GetMapping("/firestation")
    public PeopleCoveredByFireStationDto getPeopleCoveredByFireStation(@RequestParam("stationNumber") int stationNumber) {
        return null;
    }

    @GetMapping("/childAlert")
    public ChildAlertDto getChildrenFromAddress(@RequestParam("address") String address) {
        return null;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneFromPeopleCoveredByFireStation(@RequestParam("firestation") int firestation) {
        return null;
    }

    @GetMapping("/fire")
    public FireDto getPeopleAndFireStationFromAddress(@RequestParam("address") String address) {
        return null;
    }

    @GetMapping("/flood/stations")
    public List<HomeDto> getPeopleCoveredByFireStations(@RequestParam("stations") List<Integer> stations) {
        return null;
    }

    @GetMapping("/personInfo")
    public List<PersonWithMedicalsAndEmailDto> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return null;
    }

    @GetMapping("/communityEmail")
    public List<String> getCommunityEmail(@RequestParam("city") String city) {
        return null;
    }
}
