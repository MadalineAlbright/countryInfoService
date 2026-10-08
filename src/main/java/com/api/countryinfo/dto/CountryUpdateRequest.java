package com.api.countryinfo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CountryUpdateRequest {
    @NotBlank(message = "Country name is required")
    private String countryName;
    private String capitalCity;
    private String continent;
    private String currency;
    private String currencyCode;
    private String phoneCode;
    private String countryFlag;
}