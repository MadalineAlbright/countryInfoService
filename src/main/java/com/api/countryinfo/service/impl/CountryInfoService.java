package com.api.countryinfo.service.impl;

import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.dto.CountryUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CountryInfoService {
    CountryResponseDto getCountryInfo(CountryJsonRequest request);
    @Transactional
    Page<CountryResponseDto> getAllCountries(Pageable pageable);

    @Transactional
    CountryResponseDto getCountryById(Long id);

    @Transactional
    CountryResponseDto updateCountry(
            Long id,
            CountryUpdateRequest request);

    @Transactional
    void deleteCountry(Long id);
}
