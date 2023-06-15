package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.MedicalException;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.mapper.MedicalRecordMapper;
import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonService personService;
    private final MedicalRecordMapper medicalRecordMapper = MedicalRecordMapper.getInstance();

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PersonService personService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.personService = personService;
    }

    public MedicalRecordEntity getMedicalRecordEntityByName(String firstName, String lastName) {
        return medicalRecordRepository.findAll().stream().filter(medicalRecordEntity -> {
            PersonEntity personEntity = personService.getPersonEntityById(medicalRecordEntity.getPersonId());
            return personEntity.getFirstName().equals(firstName) && personEntity.getLastName().equals(lastName);
        }).findFirst().orElseThrow(() -> new MedicalException.MedicalNotFoundException("Medical record of `" + firstName + " " + lastName + "` not found"));
    }

    public Optional<MedicalRecordEntity> getOptionalMedicalRecordEntityByName(String firstName, String lastName) {
        return medicalRecordRepository.findAll().stream().filter(medicalRecordEntity -> {
            PersonEntity personEntity = personService.getPersonEntityById(medicalRecordEntity.getPersonId());
            return personEntity.getFirstName().equals(firstName) && personEntity.getLastName().equals(lastName);
        }).findFirst();
    }

    public MedicalRecordResponse getMedicalRecord(String firstName, String lastName) {
        return medicalRecordMapper.toSimpleMedicalRecordResponse(getMedicalRecordEntityByName(firstName, lastName));
    }

    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        long personId = personService.getPersonEntityByName(medicalRecordRequest.getFirstName(), medicalRecordRequest.getLastName()).getId();

        MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.save(medicalRecordMapper.toMedicalRecordEntity(medicalRecordRequest, personId));
        return medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity);
    }

    public MedicalRecordResponse updateMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        checkMedicalRecordExist(medicalRecordRequest.getFirstName(), medicalRecordRequest.getLastName());

        long personId = personService.getPersonEntityByName(medicalRecordRequest.getFirstName(), medicalRecordRequest.getLastName()).getId();
        MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.save(medicalRecordMapper.toMedicalRecordEntity(medicalRecordRequest, personId));
        return medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.delete(getMedicalRecordEntityByName(firstName, lastName));
    }

    private void checkMedicalRecordExist(String firstName, String lastName) {
        if (getOptionalMedicalRecordEntityByName(firstName, lastName).isPresent())
            throw new MedicalException.MedicalAlreadyExistsException("Medical record of `" + firstName + " " + lastName + "` already exist");
    }
}
