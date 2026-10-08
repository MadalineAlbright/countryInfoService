package com.api.countryinfo.service.impl;

import com.api.countryinfo.common.CountryMapper;
import com.api.countryinfo.dto.CountryJsonRequest;
import com.api.countryinfo.dto.CountryResponseDto;
import com.api.countryinfo.dto.CountryUpdateRequest;
import com.api.countryinfo.dto.LanguageResponseDto;
import com.api.countryinfo.model.CountryEntity;
import com.api.countryinfo.model.LanguageEntity;
import com.api.countryinfo.exception.CountryAlreadyExistsException;
import com.api.countryinfo.exception.CountryNotFoundException;
import com.api.countryinfo.exception.ExternalServiceException;
import com.api.countryinfo.generated.CountryISOCodeResponse;
import com.api.countryinfo.generated.FullCountryInfoResponse;
import com.api.countryinfo.generated.TCountryInfo;
import com.api.countryinfo.generated.TLanguage;
import com.api.countryinfo.repository.CountryRepository;
import com.api.countryinfo.webclient.CountryInfoClient;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceException;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CountryInfoServiceImpl implements CountryInfoService {
    private final CountryInfoClient countryInfoClient;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    String isoCode = null;
    public CountryInfoServiceImpl(CountryInfoClient countryInfoClient, CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryInfoClient = countryInfoClient;
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    @Transactional
    public CountryResponseDto getCountryInfo(CountryJsonRequest request) {
        try{
        // 1. Get ISO code
        CountryISOCodeResponse isoResponse =
                countryInfoClient.getCountryName(request.getName());

        isoCode = isoResponse.getCountryISOCodeResult();


        // 2. Get full country information
        FullCountryInfoResponse fullResponse =
                countryInfoClient.getFullCountryInfo(isoCode);

        TCountryInfo countryInfo =
                fullResponse.getFullCountryInfoResult();

            //Validate ISO code - check if empty
            if (countryInfo.getSISOCode() == null || countryInfo.getSISOCode().isBlank())
            { throw new CountryNotFoundException( "Country '" + request.getName() + "' was not found in the external country service." ); }

            // 3. Create Country entity
        CountryEntity countryEntity = new CountryEntity();

        countryEntity.setIsoCode(countryInfo.getSISOCode());
        countryEntity.setName(countryInfo.getSName());
        countryEntity.setCapitalCity(countryInfo.getSCapitalCity());
        countryEntity.setPhoneCode(countryInfo.getSPhoneCode());
        countryEntity.setContinentCode(countryInfo.getSContinentCode());
        countryEntity.setCurrencyIsoCode(countryInfo.getSCurrencyISOCode());
        countryEntity.setCountryFlag(countryInfo.getSCountryFlag());

        // 4. Create Language entities
        List<LanguageEntity> languageEntities = new ArrayList<>();

        if (countryInfo.getLanguages() != null &&
                countryInfo.getLanguages().getTLanguage() != null) {

            for (TLanguage language : countryInfo.getLanguages().getTLanguage()) {

                LanguageEntity languageEntity = new LanguageEntity();

                languageEntity.setIsoCode(language.getSISOCode());
                languageEntity.setName(language.getSName());
                languageEntity.setCountry(countryEntity);

                languageEntities.add(languageEntity);
            }
        }

        countryEntity.setLanguages(languageEntities);

        // 5. Save country + languages
        CountryEntity savedCountry =
                countryRepository.save(countryEntity);

        log.info("Saved entity " + savedCountry);

        // 6. Map saved entity to REST DTO
        CountryResponseDto response = new CountryResponseDto();

        response.setIsoCode(savedCountry.getIsoCode());
        response.setCountryName(savedCountry.getName());
        response.setCapitalCity(savedCountry.getCapitalCity());
        response.setPhoneCode(savedCountry.getPhoneCode());
        response.setContinent(savedCountry.getContinentCode());
        response.setCurrencyCode(savedCountry.getCurrencyIsoCode());
        response.setCountryFlag(savedCountry.getCountryFlag());

        List<LanguageResponseDto> languageDtos = new ArrayList<>();

        for (LanguageEntity language : savedCountry.getLanguages()) {

            LanguageResponseDto languageDto = new LanguageResponseDto();

            languageDto.setLanguageCode(language.getIsoCode());
            languageDto.setLanguageName(language.getName());

            languageDtos.add(languageDto);
        }

        response.setLanguages(languageDtos);

        return response;
    }catch (DataIntegrityViolationException e) {

            throw new CountryAlreadyExistsException(
                    "A country with ISO code '" + isoCode + "' already exists."
            );

        } catch (SoapFaultClientException e) { log.error( "SOAP fault while retrieving country information for {}", request.getName(), e ); throw new ExternalServiceException( "The country information service returned a SOAP fault.", e );
        } catch (WebServiceException e) { log.error( "Error communicating with country information service for {}", request.getName(), e ); throw new ExternalServiceException( "Unable to retrieve country information from the external service.", e ); }
    }

    @Transactional
    @Override
    public Page<CountryResponseDto> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(countryMapper::toResponse);
    }

    @Transactional
    @Override
    public CountryResponseDto getCountryById(Long id) {

        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() ->
                        new CountryNotFoundException(
                                "Country with ID " + id + " not found"
                        )
                );

        return countryMapper.toResponse(country);
    }

    @Transactional
    @Override
    public CountryResponseDto updateCountry(
            Long id,
            CountryUpdateRequest request) {

        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() ->
                        new CountryNotFoundException(
                                "Country with ID " + id + " not found"
                        )
                );

        country.setName(request.getCountryName());
        country.setCapitalCity(request.getCapitalCity());
        country.setPhoneCode(request.getPhoneCode());
        country.setContinentCode(request.getContinent());
        country.setCurrencyIsoCode(request.getCurrency());
        country.setCountryFlag(request.getCountryFlag());

        CountryEntity updated = countryRepository.save(country);

        return countryMapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void deleteCountry(Long id) {

        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() ->
                        new CountryNotFoundException(
                                "Country with ID " + id + " not found"
                        )
                );

        countryRepository.delete(country);
    }
}
