package com.hh99.hhplus_lecture.application.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisteredInfo {
    private final String userId;
    private final LocalDateTime registrationDate;
    private final Long lectureId;

    @Builder
    protected RegisteredInfo(String userId, LocalDateTime registrationDate, Long lectureId) {
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.lectureId = lectureId;
    }
}
