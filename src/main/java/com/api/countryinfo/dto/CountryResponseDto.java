package com.api.countryinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto {

    private Long id;

    private String isoCode;
    private String countryName;

    private String capitalCity;
    private String continent;

    private String currency;
    private String currencyCode;

    private String phoneCode;
    private String countryFlag;

    private List<LanguageResponseDto> languages;

    public CountryResponseDto(Long id, String isoCode, String name, String capitalCity, String phoneCode, String continentCode, String currencyIsoCode, String countryFlag, List<LanguageResponseDto> languages) {
    }
}