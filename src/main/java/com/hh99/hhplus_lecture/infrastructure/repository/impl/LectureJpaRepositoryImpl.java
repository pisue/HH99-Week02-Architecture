package com.hh99.hhplus_lecture.infrastructure.repository.impl;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.repository.LectureRepository;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureCapacityJpaRepository;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Repository
public class LectureJpaRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;
    private final LectureCapacityJpaRepository lectureCapacityJpaRepository;

    @Override
    public LectureInfo read(Long lectureId) {
        Lecture lecture = lectureJpaRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        LectureCapacity capacity = lectureCapacityJpaRepository.findByLectureId(lectureId);
        return LectureInfo.builder()
                .lectureId(lecture.getId())
                .lectureName(lecture.getLectureName())
                .instructor(lecture.getInstructor())
                .lectureDateTime(lecture.getLectureDateTime())
                .capacity(capacity.getCapacity())
                .currentEnrollment(capacity.getCurrentEnrollment())
                .build();
    }

    @Override
    @Transactional
    public void incrementEnrollment(Long lectureId) {
        LectureCapacity capacity = lectureCapacityJpaRepository.findByLectureId(lectureId);
        if(capacity.getCurrentEnrollment() >= capacity.getCapacity()) {
            throw new RuntimeException("강의 정원이 초과되었습니다.");
        }
        capacity.setCurrentEnrollment(capacity.getCurrentEnrollment() + 1);
        lectureCapacityJpaRepository.save(capacity);
    }

    @Override
    public List<Lecture> lectures() {
        return lectureJpaRepository.findAll();
    }

    @Override
    public List<LectureCapacity> capacities() {
        return lectureCapacityJpaRepository.findAll();
    }


}
