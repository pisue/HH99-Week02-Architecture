package com.hh99.hhplus_lecture.interfaces.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisteredLectureResponse {
    private final String userId;
    private final Long lectureId;
    private final String lectureName;
    private final String instructor;
    private final LocalDateTime lectureDateTime;
    private final LocalDateTime registrationDate;

    @Builder
    protected RegisteredLectureResponse(String userId, Long lectureId, String lectureName, String instructor,LocalDateTime lectureDateTime, LocalDateTime registrationDate) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.instructor = instructor;
        this.lectureDateTime = lectureDateTime;
        this.registrationDate = registrationDate;
    }

}
