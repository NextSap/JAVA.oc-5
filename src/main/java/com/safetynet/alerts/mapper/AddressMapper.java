package com.safetynet.alerts.mapper;

import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.request.AddressRequest;
import com.safetynet.alerts.object.response.AddressResponse;

public class AddressMapper {

    private static final AddressMapper INSTANCE = new AddressMapper();

    private AddressMapper() {
    }

    public AddressResponse toAddressDto(AddressEntity addressEntity) {
        return AddressResponse.builder()
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .zip(addressEntity.getZip())
                .build();
    }

    public AddressEntity toAddressEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .zip(addressRequest.getZip())
                .build();
    }

    public static AddressMapper getInstance() {
        return INSTANCE;
    }
}
