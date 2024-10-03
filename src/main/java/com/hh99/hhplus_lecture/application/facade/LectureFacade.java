package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.service.LectureService;
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
        // 1. LectureCapacityInfo 데이터를 lectureId 기준으로 Map으로 변환
        Map<Long, LectureCapacityInfo> lectureCapacityInfoMap = lectureService.capacities().stream()
                .collect(Collectors.toMap(LectureCapacityInfo::getLectureId, lectureCapacityInfo -> lectureCapacityInfo));

        // 2. LectureInfo 매핑하여 LectureFullInfo 생성
        List<LectureFullInfo> lectureFullInfos = lectureService.lectures().stream()
                .map(lectureInfo -> {
                    LectureCapacityInfo lectureCapacityInfo = lectureCapacityInfoMap.get(lectureInfo.getId());
                    return LectureFullInfo.builder()
                            .lectureId(lectureInfo.getId())
                            .lectureName(lectureInfo.getLectureName())
                            .instructor(lectureInfo.getInstructor())
                            .instructorId(lectureInfo.getInstructorId())
                            .lectureDateTime(lectureInfo.getLectureDateTime())
                            .capacity(lectureCapacityInfo.getCapacity())
                            .currentEnrollment(lectureCapacityInfo.getCurrentEnrollment())
                            .build();
                })
                .collect(Collectors.toList());

        return LectureInfosResponse.builder()
                .lectureFullInfos(lectureFullInfos)
                .build();
    }

    public LectureFullInfo getLectureFullInfo(Long id) {
        LectureInfo lectureInfo = lectureService.read(id);
        LectureCapacityInfo capacityInfo = lectureService.readCapacity(id);

        return LectureFullInfo.builder()
                .lectureId(lectureInfo.getId())
                .lectureName(lectureInfo.getLectureName())
                .lectureDateTime(lectureInfo.getLectureDateTime())
                .instructor(lectureInfo.getInstructor())
                .instructorId(lectureInfo.getInstructorId())
                .capacity(capacityInfo.getCapacity())
                .currentEnrollment(capacityInfo.getCurrentEnrollment())
                .build();
    }

    public void incrementEnrollment(Long lectureId) {
        lectureService.incrementEnrollment(lectureId);
    }
}
