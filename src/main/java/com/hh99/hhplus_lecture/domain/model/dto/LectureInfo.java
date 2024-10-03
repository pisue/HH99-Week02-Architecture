package com.hh99.hhplus_lecture.domain.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureInfo {
    private final Long id;
    private final String lectureName;
    private final String instructor;
    private final Long instructorId;
    private final LocalDateTime lectureDateTime;
}
