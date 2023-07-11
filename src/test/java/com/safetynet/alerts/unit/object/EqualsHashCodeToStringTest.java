package com.safetynet.alerts.unit.object;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.alerts.config.JacksonConfig;
import com.safetynet.alerts.object.entity.*;
import com.safetynet.alerts.object.model.ErrorModel;
import com.safetynet.alerts.object.model.FireStationModel;
import com.safetynet.alerts.object.model.MedicalRecordModel;
import com.safetynet.alerts.object.model.PersonModel;
import com.safetynet.alerts.object.request.*;
import com.safetynet.alerts.object.response.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EqualsHashCodeToStringTest {

    @Test
    public void testPersonModel(){
        PersonModel personModel = PersonModel.builder().build();
        PersonModel personModel1 = PersonModel.builder().build();

        assertEquals(personModel, personModel1);
        assertEquals(personModel.hashCode(), personModel1.hashCode());

        ToStringVerifier.forClass(PersonModel.class).verify();
    }

    @Test
    public void testFireStationModel() {
        FireStationModel fireStationModel = FireStationModel.builder().build();
        FireStationModel fireStationModel1 = FireStationModel.builder().build();

        assertEquals(fireStationModel, fireStationModel1);
        assertEquals(fireStationModel.hashCode(), fireStationModel1.hashCode());

        ToStringVerifier.forClass(FireStationModel.class).verify();
    }

    @Test
    public void testMedicalRecordModel() {
        MedicalRecordModel medicalRecordModel = MedicalRecordModel.builder().build();
        MedicalRecordModel medicalRecordModel1 = MedicalRecordModel.builder().build();


        assertEquals(medicalRecordModel, medicalRecordModel1);
        assertEquals(medicalRecordModel.hashCode(), medicalRecordModel1.hashCode());

        ToStringVerifier.forClass(MedicalRecordModel.class).verify();
    }

    @Test
    public void testErrorModel() {
        ErrorModel errorModel = ErrorModel.builder().build();
        ErrorModel errorModel1 = ErrorModel.builder().build();

        assertEquals(errorModel, errorModel1);
        assertEquals(errorModel.hashCode(), errorModel1.hashCode());

        ToStringVerifier.forClass(ErrorModel.class).verify();
    }

    @Test
    public void testPersonEntity() {
        PersonEntity personEntity = PersonEntity.builder().build();
        PersonEntity personEntity1 = PersonEntity.builder().build();

        assertEquals(personEntity, personEntity1);
        assertEquals(personEntity.hashCode(), personEntity1.hashCode());

        ToStringVerifier.forClass(PersonEntity.class).verify();
    }

    @Test
    public void testAddressEntity() {
        AddressEntity addressEntity = AddressEntity.builder().build();
        AddressEntity addressEntity1 = AddressEntity.builder().build();

        assertEquals(addressEntity, addressEntity1);
        assertEquals(addressEntity.hashCode(), addressEntity1.hashCode());

        ToStringVerifier.forClass(AddressEntity.class).verify();
    }

    @Test
    public void testMedicationEntity() {
        MedicationEntity medicationEntity = MedicationEntity.builder().build();
        MedicationEntity medicationEntity1 = MedicationEntity.builder().build();

        assertEquals(medicationEntity, medicationEntity1);
        assertEquals(medicationEntity.hashCode(), medicationEntity1.hashCode());

        ToStringVerifier.forClass(MedicationEntity.class).verify();
    }

    @Test
    public void testFireStationEntity() {
        FireStationEntity fireStationEntity = FireStationEntity.builder().build();
        FireStationEntity fireStationEntity1 = FireStationEntity.builder().build();

        assertEquals(fireStationEntity, fireStationEntity1);
        assertEquals(fireStationEntity.hashCode(), fireStationEntity1.hashCode());

        ToStringVerifier.forClass(FireStationEntity.class).verify();
    }

    @Test
    public void testMedicalRecordEntity() {
        MedicalRecordEntity medicalRecordEntity = MedicalRecordEntity.builder().build();
        MedicalRecordEntity medicalRecordEntity1 = MedicalRecordEntity.builder().build();

        assertEquals(medicalRecordEntity, medicalRecordEntity1);
        assertEquals(medicalRecordEntity.hashCode(), medicalRecordEntity1.hashCode());

        ToStringVerifier.forClass(MedicalRecordEntity.class).verify();
    }

    @Test
    public void testPersonRequest() {
        PersonRequest personRequest = PersonRequest.builder().build();
        PersonRequest personRequest1 = PersonRequest.builder().build();

        assertEquals(personRequest, personRequest1);
        assertEquals(personRequest.hashCode(), personRequest1.hashCode());

        ToStringVerifier.forClass(PersonRequest.class).verify();
    }

    @Test
    public void testMedicalRecordRequest() {
        MedicalRecordRequest medicalRecordRequest = MedicalRecordRequest.builder().build();
        MedicalRecordRequest medicalRecordRequest1 = MedicalRecordRequest.builder().build();

        assertEquals(medicalRecordRequest, medicalRecordRequest1);
        assertEquals(medicalRecordRequest.hashCode(), medicalRecordRequest1.hashCode());

        ToStringVerifier.forClass(MedicalRecordRequest.class).verify();
    }

    @Test
    public void testAddressRequest() {
        AddressRequest addressRequest = AddressRequest.builder().build();
        AddressRequest addressRequest1 = AddressRequest.builder().build();

        assertEquals(addressRequest, addressRequest1);
        assertEquals(addressRequest.hashCode(), addressRequest1.hashCode());

        ToStringVerifier.forClass(AddressRequest.class).verify();
    }

    @Test
    public void testMedicationRequest() {
        MedicationRequest medicationRequest = MedicationRequest.builder().build();
        MedicationRequest medicationRequest1 = MedicationRequest.builder().build();

        assertEquals(medicationRequest, medicationRequest1);
        assertEquals(medicationRequest.hashCode(), medicationRequest1.hashCode());

        ToStringVerifier.forClass(MedicationRequest.class).verify();
    }

    @Test
    public void testFireStationRequest() {
        FireStationRequest fireStationRequest = FireStationRequest.builder().build();
        FireStationRequest fireStationRequest1 = FireStationRequest.builder().build();

        assertEquals(fireStationRequest, fireStationRequest1);
        assertEquals(fireStationRequest.hashCode(), fireStationRequest1.hashCode());

        ToStringVerifier.forClass(FireStationRequest.class).verify();
    }

    @Test
    public void testPersonResponse() {
        PersonResponse personResponse = PersonResponse.builder().build();
        PersonResponse personResponse1 = PersonResponse.builder().build();

        assertEquals(personResponse, personResponse1);
        assertEquals(personResponse.hashCode(), personResponse1.hashCode());

        ToStringVerifier.forClass(PersonResponse.class).verify();
    }

    @Test
    public void testMedicalRecordResponse() {
        MedicalRecordResponse medicalRecordResponse = MedicalRecordResponse.builder().build();
        MedicalRecordResponse medicalRecordResponse1 = MedicalRecordResponse.builder().build();

        assertEquals(medicalRecordResponse, medicalRecordResponse1);
        assertEquals(medicalRecordResponse.hashCode(), medicalRecordResponse1.hashCode());

        ToStringVerifier.forClass(MedicalRecordResponse.class).verify();
    }

    @Test
    public void testHomeResponse() {
        HomeResponse homeResponse = HomeResponse.builder().build();
        HomeResponse homeResponse1 = HomeResponse.builder().build();

        assertEquals(homeResponse, homeResponse1);
        assertEquals(homeResponse.hashCode(), homeResponse1.hashCode());

        ToStringVerifier.forClass(HomeResponse.class).verify();
    }

    @Test
    public void testAddressResponse() {
        AddressResponse addressResponse = AddressResponse.builder().build();
        AddressResponse addressResponse1 = AddressResponse.builder().build();

        assertEquals(addressResponse, addressResponse1);
        assertEquals(addressResponse.hashCode(), addressResponse1.hashCode());

        ToStringVerifier.forClass(AddressResponse.class).verify();
    }

    @Test
    public void testPeopleCoveredByFireStationResponse() {
        PeopleCoveredByFireStationResponse peopleCoveredByFireStationResponse = PeopleCoveredByFireStationResponse.builder().build();
        PeopleCoveredByFireStationResponse peopleCoveredByFireStationResponse1 = PeopleCoveredByFireStationResponse.builder().build();

        assertEquals(peopleCoveredByFireStationResponse, peopleCoveredByFireStationResponse1);
        assertEquals(peopleCoveredByFireStationResponse.hashCode(), peopleCoveredByFireStationResponse1.hashCode());

        ToStringVerifier.forClass(PeopleCoveredByFireStationResponse.class).verify();
    }

    @Test
    public void testMedicationResponse() {
        MedicationResponse medicationResponse = MedicationResponse.builder().build();
        MedicationResponse medicationResponse1 = MedicationResponse.builder().build();

        assertEquals(medicationResponse, medicationResponse1);
        assertEquals(medicationResponse.hashCode(), medicationResponse1.hashCode());

        ToStringVerifier.forClass(MedicationResponse.class).verify();
    }

    @Test
    public void testChildAlertResponse() {
        ChildAlertResponse childAlertResponse = ChildAlertResponse.builder().build();
        ChildAlertResponse childAlertResponse1 = ChildAlertResponse.builder().build();

        assertEquals(childAlertResponse, childAlertResponse1);
        assertEquals(childAlertResponse.hashCode(), childAlertResponse1.hashCode());

        ToStringVerifier.forClass(ChildAlertResponse.class).verify();
    }

    @Test
    public void testFireStationResponse() {
        FireStationResponse fireStationResponse = FireStationResponse.builder().build();
        FireStationResponse fireStationResponse1 = FireStationResponse.builder().build();

        assertEquals(fireStationResponse, fireStationResponse1);
        assertEquals(fireStationResponse.hashCode(), fireStationResponse1.hashCode());

        ToStringVerifier.forClass(FireStationResponse.class).verify();
    }

    @Test
    public void testFireResponse() {
        FireResponse fireResponse = FireResponse.builder().build();
        FireResponse fireResponse1 = FireResponse.builder().build();

        assertEquals(fireResponse, fireResponse1);
        assertEquals(fireResponse.hashCode(), fireResponse1.hashCode());

        ToStringVerifier.forClass(FireResponse.class).verify();
    }

    @Test
    public void testJacksonConfigModels() {
        JacksonConfig.Models models = new JacksonConfig.Models();
        JacksonConfig.Models models1 = new JacksonConfig.Models();

        assertEquals(models, models1);
        assertEquals(models.hashCode(), models1.hashCode());

        ToStringVerifier.forClass(JacksonConfig.Models.class).verify();
    }

    @Test
    public void testJacksonConfigEntities() {
        JacksonConfig.Entities entities = new JacksonConfig.Entities();
        JacksonConfig.Entities entities1 = new JacksonConfig.Entities();

        assertEquals(entities, entities1);
        assertEquals(entities.hashCode(), entities1.hashCode());

        ToStringVerifier.forClass(JacksonConfig.Entities.class).verify();
    }
}
