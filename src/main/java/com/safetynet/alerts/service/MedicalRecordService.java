package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDto;
import com.safetynet.alerts.dto.SimpleMedicalRecordDto;
import com.safetynet.alerts.entity.MedicalRecordEntity;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.mapper.MedicalRecordMapper;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }).findFirst().orElseThrow();
    }

    public SimpleMedicalRecordDto getMedicalRecord(String firstName, String lastName) {
        return medicalRecordMapper.toSimpleMedicalRecordDto(getMedicalRecordEntityByName(firstName, lastName));
    }

    public MedicalRecordDto createMedicalRecord(MedicalRecordDto medicalRecordDto) {
        long personId = personService.getPersonEntity(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName()).getId();
        medicalRecordRepository.save(medicalRecordMapper.toMedicalRecordEntity(medicalRecordDto, personId));
        return medicalRecordDto;
    }

    public MedicalRecordDto updateMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecordEntity medicalRecordEntity = getMedicalRecordEntityByName(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName());
        long personId = personService.getPersonEntity(medicalRecordDto.getFirstName(), medicalRecordDto.getLastName()).getId();
        medicalRecordRepository.save(medicalRecordMapper.toMedicalRecordEntity(medicalRecordDto, personId));
        return medicalRecordDto;
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.delete(getMedicalRecordEntityByName(firstName, lastName));
    }
}
