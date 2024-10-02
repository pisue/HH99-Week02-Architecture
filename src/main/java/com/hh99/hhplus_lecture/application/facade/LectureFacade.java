package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.service.LectureService;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.interfaces.api.response.LectureInfosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LectureFacade {
    private final LectureService lectureService;

    public LectureInfosResponse lectureInfos() {
        // 1. Lecture 데이터를 lectureId 기준으로 Map으로 변환
        Map<Long, Lecture> lectureMap = lectureService.lectures().stream()
                .collect(Collectors.toMap(Lecture::getId, lecture -> lecture));

        // 2. LectureCapacity와 매핑하여 LectureInfo 생성
        List<LectureInfo> lectureInfos = lectureService.capacities().stream()
                .map(capacity -> {
                    Lecture lecture = lectureMap.get(capacity.getLectureId());
                    return LectureInfo.builder()
                            .lectureId(lecture.getId())
                            .lectureName(lecture.getLectureName())
                            .lectureDateTime(lecture.getLectureDateTime())
                            .capacity(capacity.getCapacity())
                            .currentEnrollment(capacity.getCurrentEnrollment())
                            .build();
                })
                .collect(Collectors.toList());

        return LectureInfosResponse.builder()
                .lectureInfos(lectureInfos)
                .build();
    }
}
