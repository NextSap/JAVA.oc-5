package com.safetynet.alerts.mapper;

import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.object.request.PersonRequest;
import com.safetynet.alerts.object.response.PeopleCoveredByFireStationResponse;
import com.safetynet.alerts.object.response.PersonResponse;
import com.safetynet.alerts.utils.DateUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonMapper {

    private static final PersonMapper INSTANCE = new PersonMapper();
    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private final MedicalRecordMapper medicalRecordMapper = MedicalRecordMapper.getInstance();
    private final DateUtils dateUtils = DateUtils.getInstance();

    private PersonMapper() {
    }

    public PersonResponse toPersonResponse(PersonEntity personEntity) {
        return PersonResponse.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .phone(personEntity.getPhone())
                .email(personEntity.getEmail())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .birthdate(dateUtils.getFormattedDate(dateUtils.getDate(personEntity.getBirthdate())))
                .age(dateUtils.getAge(dateUtils.getDate(personEntity.getBirthdate())))
                .build();
    }

    public PersonResponse toPersonResponse(PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        return PersonResponse.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .phone(personEntity.getPhone())
                .email(personEntity.getEmail())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .birthdate(dateUtils.getFormattedDate(dateUtils.getDate(personEntity.getBirthdate())))
                .medicalRecord(medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity))
                .age(dateUtils.getAge(dateUtils.getDate(personEntity.getBirthdate())))
                .build();
    }

    public PersonEntity toPersonEntity(PersonRequest personRequest) {
        return PersonEntity.builder()
                .firstName(personRequest.getFirstName())
                .lastName(personRequest.getLastName())
                .phone(personRequest.getPhone())
                .email(personRequest.getEmail())
                .address(addressMapper.toAddressEntity(personRequest.getAddress()))
                .birthdate(DateUtils.getInstance().getDate(personRequest.getBirthdate()).getTime())
                .build();
    }

    public PersonEntity toPersonEntity(PersonRequest personRequest, long id) {
        return PersonEntity.builder()
                .id(id)
                .firstName(personRequest.getFirstName())
                .lastName(personRequest.getLastName())
                .phone(personRequest.getPhone())
                .email(personRequest.getEmail())
                .address(addressMapper.toAddressEntity(personRequest.getAddress()))
                .birthdate(DateUtils.getInstance().getDate(personRequest.getBirthdate()).getTime())
                .build();
    }

    public List<PersonResponse> toPersonResponseList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(this::toPersonResponse).collect(Collectors.toList());
    }

    public PersonResponse toPersonWithMedicalsAndEmailResponse(PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        return PersonResponse.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .email(personEntity.getEmail())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .birthdate(dateUtils.getFormattedDate(dateUtils.getDate(personEntity.getBirthdate())))
                .medicalRecord(medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity))
                .age(dateUtils.getAge(dateUtils.getDate(personEntity.getBirthdate())))
                .build();
    }

    public PersonResponse toPersonWithMedicalsResponse(PersonEntity personEntity, MedicalRecordEntity medicalRecordEntity) {
        return PersonResponse.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .address(addressMapper.toAddressDto(personEntity.getAddress()))
                .birthdate(dateUtils.getFormattedDate(dateUtils.getDate(personEntity.getBirthdate())))
                .medicalRecord(medicalRecordMapper.toMedicalRecordResponse(medicalRecordEntity))
                .age(dateUtils.getAge(dateUtils.getDate(personEntity.getBirthdate())))
                .build();
    }

    public List<PersonResponse> toPersonWithMedicalsResponseList(Map<PersonEntity, MedicalRecordEntity> personMedicalRecordEntityMap) {
       return personMedicalRecordEntityMap.entrySet().stream().map(entry -> toPersonWithMedicalsResponse(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    public PeopleCoveredByFireStationResponse toPeopleCoveredByFireStationDto(List<PersonEntity> people) {
        return PeopleCoveredByFireStationResponse.builder()
                .people(toPersonResponseList(people))
                .adults((int) people.stream().filter(person -> dateUtils.getAge(dateUtils.getDate(person.getBirthdate())) > 18).count())
                .children((int) people.stream().filter(person -> dateUtils.getAge(dateUtils.getDate(person.getBirthdate())) <= 18).count())
                .build();
    }

    public static PersonMapper getInstance() {
        return INSTANCE;
    }
}
