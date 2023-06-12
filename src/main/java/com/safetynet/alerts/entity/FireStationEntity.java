package com.safetynet.alerts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "firestation")
public class FireStationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int station;
    @ElementCollection
    private List<String> addresses;

    public void addAddress(String... address) {
        this.addresses.addAll(Arrays.asList(address));
    }
}
