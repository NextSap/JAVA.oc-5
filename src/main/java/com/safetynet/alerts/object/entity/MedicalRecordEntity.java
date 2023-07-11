package com.safetynet.alerts.object.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "medicalrecord")
public class MedicalRecordEntity {
    @Id
    private long personId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicationEntity> medications;
    @ElementCollection
    private List<String> allergies;
}
