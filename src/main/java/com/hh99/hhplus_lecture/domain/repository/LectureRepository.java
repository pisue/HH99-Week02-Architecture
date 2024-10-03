package com.hh99.hhplus_lecture.domain.repository;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;

import java.util.List;

public interface LectureRepository {
    LectureInfo read(Long lectureId);

    void incrementEnrollment(Long lectureId);

    List<LectureInfo> lectures();

    List<LectureCapacityInfo> capacities();

    LectureCapacityInfo readCapacity(Long lectureId);
}