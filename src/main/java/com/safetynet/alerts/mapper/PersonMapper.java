package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.entity.PersonEntity;

public class PersonMapper {

    private final AddressMapper addressMapper = new AddressMapper();
    private final MedicalRecordMapper medicalRecordMapper = new MedicalRecordMapper();

    public PersonDto toPersonDto(PersonEntity personEntity) {
        return PersonDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .phone(personEntity.getPhone())
                .email(personEntity.getEmail())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .medicalRecord(medicalRecordMapper.toMedicalRecordDto(personEntity.getMedicalRecord()))
                .build();
    }

    public PersonEntity toPersonEntity(PersonDto personDto) {
        return PersonEntity.builder()
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .phone(personDto.getPhone())
                .email(personDto.getEmail())
                .address(addressMapper.toAddressEntity(personDto.getAddress()))
                .medicalRecord(medicalRecordMapper.toMedicalRecordEntity(personDto.getMedicalRecord()))
                .build();
    }
}
