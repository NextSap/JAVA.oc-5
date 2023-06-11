package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.ChildDto;
import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.dto.PersonWithMedicalsAndEmailDto;
import com.safetynet.alerts.dto.SimplePersonDto;
import com.safetynet.alerts.entity.PersonEntity;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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
                .birthdate(personEntity.getBirthdate())
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
                .birthdate(personDto.getBirthdate())
                .medicalRecord(medicalRecordMapper.toMedicalRecordEntity(personDto.getMedicalRecord()))
                .build();
    }

    public ChildDto toChildDto(PersonEntity personEntity) {
        Calendar calendar = new Calendar.Builder().setInstant(personEntity.getBirthdate()).build();
        return ChildDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .age(Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR))
                .build();
    }

    public List<ChildDto> toChildDtoList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(this::toChildDto).collect(Collectors.toList());
    }

    public SimplePersonDto toSimplePersonDto(PersonEntity personEntity) {
        return SimplePersonDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .build();
    }

    public List<SimplePersonDto> toSimplePersonDtoList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(this::toSimplePersonDto).collect(Collectors.toList());
    }

    public PersonWithMedicalsAndEmailDto toPersonWithMedicalsAndEmailDto(PersonEntity personEntity) {
        return PersonWithMedicalsAndEmailDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .email(personEntity.getEmail())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .medicalRecord(medicalRecordMapper.toMedicalRecordDto(personEntity.getMedicalRecord()))
                .build();
    }
}
