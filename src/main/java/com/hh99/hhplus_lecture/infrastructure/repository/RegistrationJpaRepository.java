package com.hh99.hhplus_lecture.infrastructure.repository;

import com.hh99.hhplus_lecture.infrastructure.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {
    // userId와 lectureId로 이미 등록된 정보가 있는지 확인하는 메서드
    Optional<Registration> findByUserIdAndLectureId(String userId, Long lectureId);
}
