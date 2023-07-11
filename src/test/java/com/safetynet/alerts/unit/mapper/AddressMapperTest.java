package com.safetynet.alerts.unit.mapper;

import com.safetynet.alerts.mapper.AddressMapper;
import com.safetynet.alerts.object.entity.AddressEntity;
import com.safetynet.alerts.object.request.AddressRequest;
import com.safetynet.alerts.object.response.AddressResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressMapperTest {

    private final AddressMapper addressMapper = AddressMapper.getInstance();
    private static AddressResponse addressResponse;
    private static AddressEntity addressEntity;
    private static AddressRequest addressRequest;

    @BeforeAll
    public static void setUp() {
        addressResponse = AddressResponse.builder()
                .street("street")
                .city("city")
                .zip("zip")
                .build();

        addressEntity = AddressEntity.builder()
                .id(1)
                .street("street")
                .city("city")
                .zip("zip")
                .build();

        addressRequest = AddressRequest.builder()
                .street("street")
                .city("city")
                .zip("zip")
                .build();
    }

    @Test
    public void toAddressResponseTest() {
        assertEquals(addressResponse, addressMapper.toAddressResponse(addressEntity));
    }

    @Test
    public void toAddressEntityTest() {
        assertEquals(AddressEntity.builder().street("street").city("city").zip("zip").build(), addressMapper.toAddressEntity(addressRequest));
    }

}
