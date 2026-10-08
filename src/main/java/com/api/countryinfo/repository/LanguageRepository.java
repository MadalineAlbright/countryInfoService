package com.api.countryinfo.repository;

import com.api.countryinfo.model.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
}