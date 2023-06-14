package com.safetynet.alerts.dto;

import com.safetynet.alerts.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PersonDto {
    @NotBlank(message = "firstName:Required") @NotNull(message = "firstName:Null")
    private String firstName;
    @NotBlank(message = "lastName:Required") @NotNull(message = "lastName:Null")
    private String lastName;
    @NotBlank(message = "phone:Required") @NotNull(message = "phone:Null")
    private String phone;
    @NotBlank(message = "email:Required") @NotNull(message = "email:Null")
    private String email;
    @NotBlank(message = "birthdate:Required") @NotNull(message = "birthdate:Null")
    @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(\\d{4})$", message = "birthdate:Invalid (dd/MM/yyyy)")
    private String birthdate;
    @NotNull(message = "address:Null")
    private AddressDto address;

    public static PersonDto mock() {
        return PersonDto.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .phone("Phone")
                .email("Email")
                .birthdate(DateUtils.getInstance().getFormattedDate(new Date(), "dd/MM/yyyy"))
                .address(AddressDto.builder().city("City").zip("Zip").street("Street").build())
                .build();
    }
}
