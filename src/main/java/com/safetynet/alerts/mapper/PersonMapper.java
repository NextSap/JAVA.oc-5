package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.utils.DateUtils;

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
        return ChildDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .age(DateUtils.getAge(personEntity.getBirthdate()))
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

    public PersonWithMedicalsDto toPersonWithMedicalsDto(PersonEntity personEntity) {
        return PersonWithMedicalsDto.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .phone(personEntity.getPhone())
                .age(DateUtils.getAge(personEntity.getBirthdate()))
                .medicalRecord(medicalRecordMapper.toMedicalRecordDto(personEntity.getMedicalRecord()))
                .build();
    }

    public List<PersonWithMedicalsDto> toPersonWithMedicalsDtoList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(this::toPersonWithMedicalsDto).collect(Collectors.toList());
    }

    public List<PersonDto> toPersonDtoList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(this::toPersonDto).collect(Collectors.toList());
    }

    public PeopleCoveredByFireStationDto toPeopleCoveredByFireStationDto(List<PersonEntity> personEntityList) {
        return PeopleCoveredByFireStationDto.builder()
                .people(toPersonDtoList(personEntityList))
                .adults((int) personEntityList.stream().filter(personEntity -> DateUtils.getInstance().isMajor(personEntity.getBirthdate())).count())
                .children((int) personEntityList.stream().filter(personEntity -> !DateUtils.getInstance().isMajor(personEntity.getBirthdate())).count())
                .build();
    }
}
