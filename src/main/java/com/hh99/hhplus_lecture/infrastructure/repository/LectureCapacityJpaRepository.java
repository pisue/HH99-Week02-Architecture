package com.hh99.hhplus_lecture.infrastructure.repository;

import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacity, Long> {
    LectureCapacity findByLectureId(Long lectureId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lc FROM LectureCapacity lc WHERE lc.lectureId = :lectureId")
    LectureCapacity findByLectureIdWithLock(Long lectureId);
}
