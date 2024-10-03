package com.hh99.hhplus_lecture.interfaces.api.response;

import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LectureInfosResponse {
    private final List<LectureFullInfo> lectureFullInfos;

    @Builder
    protected LectureInfosResponse(List<LectureFullInfo> lectureFullInfos) {
        this.lectureFullInfos = lectureFullInfos;
    }
}
