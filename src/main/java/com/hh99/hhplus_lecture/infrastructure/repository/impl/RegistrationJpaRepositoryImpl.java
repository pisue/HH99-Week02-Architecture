package com.hh99.hhplus_lecture.infrastructure.repository.impl;

import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.repository.RegistrationRepository;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Registration;
import com.hh99.hhplus_lecture.infrastructure.repository.RegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

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
    public boolean checkRegistration(String userId, Long lectureId) {
        return registrationJpaRepository.findByUserIdAndLectureId(userId, lectureId).isPresent();
    }
}
