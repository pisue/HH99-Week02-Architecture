package com.hh99.hhplus_lecture.interfaces.api.response;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LectureInfosResponse {
    private final List<LectureInfo> lectureInfos;

    @Builder
    protected LectureInfosResponse(List<LectureInfo> lectureInfos) {
        this.lectureInfos = lectureInfos;
    }
}
