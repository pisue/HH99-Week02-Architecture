package com.hh99.hhplus_lecture.domain.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureInfo {
    private final Long lectureId;
    private final String lectureName;
    private final String instructor;
    private final LocalDateTime lectureDateTime;
    private final int capacity;
    private final int currentEnrollment;

    @Builder
    public LectureInfo(Long lectureId, String lectureName, String instructor,
                       LocalDateTime lectureDateTime, int capacity, int currentEnrollment) {
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.instructor = instructor;
        this.lectureDateTime = lectureDateTime;
        this.capacity = capacity;
        this.currentEnrollment = currentEnrollment;
    }
}
