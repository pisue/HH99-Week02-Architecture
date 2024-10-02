package com.hh99.hhplus_lecture.domain.repository;

import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;

public interface RegistrationRepository {
    void register(RegistrationCommand registrationCommand);
}