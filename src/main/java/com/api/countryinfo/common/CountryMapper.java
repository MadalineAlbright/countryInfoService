package com.api.countryinfo.common;

import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.dto.LanguageResponseDto;
import com.api.countryinfo.model.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryMapper {

    public CountryResponseDto toResponse(CountryEntity country) {

        CountryResponseDto response = new CountryResponseDto();

        response.setId(country.getId());
        response.setIsoCode(country.getIsoCode());
        response.setCountryName(country.getName());
        response.setCapitalCity(country.getCapitalCity());
        response.setPhoneCode(country.getPhoneCode());
        response.setContinent(country.getContinentCode());
        response.setCurrencyCode(country.getCurrencyIsoCode());
        response.setCountryFlag(country.getCountryFlag());

        List<LanguageResponseDto> languages = country.getLanguages()
                .stream()
                .map(language -> new LanguageResponseDto(
                        language.getIsoCode(),
                        language.getName()
                ))
                .toList();

        response.setLanguages(languages);

        return response;
    }
    public List<CountryResponseDto> toResponseList( List<CountryEntity> countries) { return countries.stream() .map(this::toResponse) .toList(); } }