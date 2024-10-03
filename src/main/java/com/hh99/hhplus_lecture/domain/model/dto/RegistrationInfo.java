package com.hh99.hhplus_lecture.domain.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegistrationInfo {
    private Long id;
    private String userid;
    private Long lectureId;
    private LocalDateTime registrationTime;
}
