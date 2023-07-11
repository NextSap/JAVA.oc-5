package com.safetynet.alerts.object.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PersonRequest implements Serializable {
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
    private AddressRequest address;
}
