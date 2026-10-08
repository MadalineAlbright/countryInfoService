package com.api.countryinfo.controller;

import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.dto.CountryUpdateRequest;
import com.api.countryinfo.service.impl.CountryInfoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<Page<CountryResponseDto>> getAllCountries(
            @PageableDefault(size = 20, sort = "id")
            Pageable pageable) {
        return ResponseEntity.ok(countryInfoService.getAllCountries(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDto> getCountryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                countryInfoService.getCountryById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponseDto> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryUpdateRequest request) {

        return ResponseEntity.ok(
                countryInfoService.updateCountry(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(
            @PathVariable Long id) {

        countryInfoService.deleteCountry(id);

        return ResponseEntity.noContent().build();
    }
}
