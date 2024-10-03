package com.hh99.hhplus_lecture.infrastructure.repository.impl;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.repository.LectureRepository;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureCapacityJpaRepository;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Repository
public class LectureJpaRepositoryImpl implements LectureRepository {
    private final LectureJpaRepository lectureJpaRepository;
    private final LectureCapacityJpaRepository lectureCapacityJpaRepository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public LectureInfo read(Long id) {
        Lecture lecture = lectureJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("강의 정보가 존재하지 않습니다."));

        return LectureInfo.builder()
                .id(lecture.getId())
                .lectureDateTime(lecture.getLectureDateTime())
                .lectureName(lecture.getLectureName())
                .instructor(lecture.getInstructor())
                .instructorId(lecture.getInstructorId())
                .build();
    }

    @Override
    public LectureCapacityInfo readCapacity(Long lectureId) {
        LectureCapacity capacity = lectureCapacityJpaRepository.findByLectureId(lectureId);
        if (capacity == null) {
            throw new RuntimeException("특강 수강인원 정보가 존재하지 않습니다.");
        }
        return LectureCapacityInfo.builder()
                .lectureId(capacity.getId())
                .capacity(capacity.getCapacity())
                .currentEnrollment(capacity.getCurrentEnrollment())
                .build();
    }

    @Override
    public List<LectureFullInfo> findLecturesAfterDate(LocalDate date) {
        LocalDateTime dateTime = date.atStartOfDay();
        List<Lecture> lectures = lectureJpaRepository.findByLectureDateTimeAfter(dateTime);
        return lectures.stream()
                .map(lecture -> {
                    LectureCapacity capacity = lectureCapacityJpaRepository.findByLectureId(lecture.getId());
                    return LectureFullInfo.builder()
                            .lectureId(lecture.getId())
                            .lectureName(lecture.getLectureName())
                            .instructor(lecture.getInstructor())
                            .instructorId(lecture.getInstructorId())
                            .lectureDateTime(lecture.getLectureDateTime())
                            .capacity(capacity.getCapacity())
                            .currentEnrollment(capacity.getCurrentEnrollment())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void incrementEnrollment(Long lectureId) {
        LectureCapacity capacity = lectureCapacityJpaRepository.findByLectureIdWithLock(lectureId);
        if(capacity.getCurrentEnrollment() >= capacity.getCapacity()) {
            throw new RuntimeException("강의 정원이 초과되었습니다.");
        }
        System.out.println("capacity.getCurrentEnrollment()::::::"+capacity.getCurrentEnrollment());
        capacity.setCurrentEnrollment(capacity.getCurrentEnrollment()+1);
        lectureCapacityJpaRepository.save(capacity);
    }

    @Override
    public List<LectureInfo> lectures() {
        return lectureJpaRepository.findAll().stream()
                .map(lecture -> LectureInfo.builder()
                        .id(lecture.getId())
                        .lectureName(lecture.getLectureName())
                        .instructor(lecture.getInstructor())
                        .instructorId(lecture.getInstructorId())
                        .lectureDateTime(lecture.getLectureDateTime())
                        .build()

                )
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureCapacityInfo> capacities() {
        return lectureCapacityJpaRepository.findAll().stream()
                .map(lectureCapacity -> LectureCapacityInfo.builder()
                        .lectureId(lectureCapacity.getLectureId())
                        .capacity(lectureCapacity.getCapacity())
                        .currentEnrollment(lectureCapacity.getCurrentEnrollment())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
