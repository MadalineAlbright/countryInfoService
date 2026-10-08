package com.api.countryinfo.repository;

import com.api.countryinfo.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findByIsoCode(String isoCode);
    boolean existsByIsoCode(String isoCode);
}
