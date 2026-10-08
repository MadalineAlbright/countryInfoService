package com.api.countryinfo.controller;

import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.service.impl.CountryInfoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountryInfoController {
    private final CountryInfoService countryInfoService;
    public CountryInfoController(CountryInfoService countryInfoService) {
        this.countryInfoService = countryInfoService;
    }
    @PostMapping
    public ResponseEntity<CountryResponseDto> getCountry(
            @Valid @RequestBody CountryJsonRequest request) {
        CountryResponseDto response =
                countryInfoService.getCountryInfo(request);
        return ResponseEntity.ok(response);
    }
}
