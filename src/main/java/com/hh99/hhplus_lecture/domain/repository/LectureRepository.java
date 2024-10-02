package com.hh99.hhplus_lecture.domain.repository;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;

import java.util.List;

public interface LectureRepository {
    LectureInfo read(Long lectureId);

    void incrementEnrollment(Long lectureId);

    List<Lecture> lectures();

    List<LectureCapacity> capacities();
}