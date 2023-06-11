package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.AddressDto;
import com.safetynet.alerts.entity.AddressEntity;

public class AddressMapper {

    public AddressDto toAddressDto(AddressEntity addressEntity) {
        return AddressDto.builder()
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .zip(addressEntity.getZip())
                .build();
    }

    public AddressEntity toAddressEntity(AddressDto addressDto) {
        return AddressEntity.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .zip(addressDto.getZip())
                .build();
    }
}
