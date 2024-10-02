package com.hh99.hhplus_lecture.infrastructure.repository;

import com.hh99.hhplus_lecture.infrastructure.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

}
