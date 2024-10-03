package com.hh99.hhplus_lecture.infrastructure.repository;

import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    Lecture findById(long id);

    List<Lecture> findByLectureDateTimeAfter(LocalDateTime date);
}
