package com.hh99.hhplus_lecture.infrastructure.repository.impl;

import com.hh99.hhplus_lecture.application.model.dto.RegisteredInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.repository.RegistrationRepository;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Registration;
import com.hh99.hhplus_lecture.infrastructure.repository.RegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Repository
public class RegistrationJpaRepositoryImpl implements RegistrationRepository {
    private final RegistrationJpaRepository registrationJpaRepository;

    @Override
    public void register(RegistrationCommand registrationCommand) {
        Registration registration = new Registration(registrationCommand);
        registrationJpaRepository.save(registration);
    }

    @Override
    public boolean checkRegistration(RegistrationCommand registrationCommand) {
        return registrationJpaRepository.findByUserIdAndLectureId(registrationCommand.getUserId(), registrationCommand.getLectureId()).isPresent();
    }

    @Override
    public List<RegisteredInfo> findByUserId(String userId) {
        return registrationJpaRepository.findByUserId(userId).stream()
                .map(registration -> RegisteredInfo.builder()
                        .lectureId(registration.getLectureId())
                        .userId(registration.getUserId())
                        .registrationDate(registration.getRegistrationDate())
                        .build())
                .collect(Collectors.toList());
    }
}
