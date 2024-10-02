package com.hh99.hhplus_lecture.domain.service;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.repository.LectureRepository;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureInfo read(Long lectureId) {
        return lectureRepository.read(lectureId);
    }

    public void incrementEnrollment(Long lectureId) {
        lectureRepository.incrementEnrollment(lectureId);
    }


    public List<Lecture> lectures() {
        return lectureRepository.lectures();
    }

    public List<LectureCapacity> capacities() {
        return lectureRepository.capacities();
    }
}

