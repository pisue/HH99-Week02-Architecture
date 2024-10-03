package com.hh99.hhplus_lecture.domain.repository;

import com.hh99.hhplus_lecture.application.model.dto.RegisteredInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;

import java.util.List;

public interface RegistrationRepository {
    void register(RegistrationCommand registrationCommand);

    boolean checkRegistration(RegistrationCommand registrationCommand);

    List<RegisteredInfo> findByUserId(String userId);
}