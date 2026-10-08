package com.api.countryinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class CountryJsonRequest {

    @NotBlank(message = "Country name is required")
    @Pattern(
            regexp = "[A-Z].*",
            message = "Country name must start with a capital letter, e.g. Kenya or South Africa"
    )
    private String name;

    public CountryJsonRequest(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
