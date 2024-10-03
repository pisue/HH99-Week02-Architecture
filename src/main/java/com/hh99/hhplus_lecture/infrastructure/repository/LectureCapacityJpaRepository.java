package com.hh99.hhplus_lecture.infrastructure.repository;

import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacity, Long> {
    LectureCapacity findByLectureId(Long lectureId);
}
