package com.api.countryinfo.service.impl;

import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryJsonResponse;
import com.api.countryinfo.dto.CountryResponseDto;

public interface CountryInfoService {
    CountryResponseDto getCountryInfo(CountryJsonRequest request);
}
