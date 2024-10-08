package com.hh99.hhplus_lecture.domain.service;

import com.hh99.hhplus_lecture.application.model.dto.RegisteredInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public void register(RegistrationCommand registrationCommand) {
        registrationRepository.register(registrationCommand);
    }

    public boolean checkRegistration(RegistrationCommand registrationCommand) {
        return registrationRepository.checkRegistration(registrationCommand);
    }

    public List<RegisteredInfo> getRegistrationsByUserId(String userId) {
        return registrationRepository.findByUserId(userId);
    }
}
