package com.hh99.hhplus_lecture.domain.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureCapacityInfo {
    private final Long lectureId;
    private final int capacity;
    private final int currentEnrollment;
}
