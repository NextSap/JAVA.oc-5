package com.safetynet.alerts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    @NotBlank(message = "street:Required") @NotNull(message = "street:Null")
    private String street;
    @NotBlank(message = "city:Required") @NotNull(message = "city:Null")
    private String city;
    @NotBlank(message = "zip:Required") @NotNull(message = "zip:Null")
    private String zip;
}
