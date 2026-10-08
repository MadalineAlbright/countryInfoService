package com.api.countryinfo.service.impl;

import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryJsonResponse;
import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.dto.CountryUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CountryInfoService {
    CountryResponseDto getCountryInfo(CountryJsonRequest request);

    @Transactional
    List<CountryResponseDto> getAllCountries();

    @Transactional
    CountryResponseDto getCountryById(Long id);

    @Transactional
    CountryResponseDto updateCountry(
            Long id,
            CountryUpdateRequest request);

    @Transactional
    void deleteCountry(Long id);
}
