package com.safetynet.alerts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date birthdate;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalRecordEntity medicalRecord;
}
