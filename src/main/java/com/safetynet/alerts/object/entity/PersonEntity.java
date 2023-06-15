package com.safetynet.alerts.object.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long birthdate;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;
}
