package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.service.LectureService;
import com.hh99.hhplus_lecture.domain.service.RegistrationService;
import com.hh99.hhplus_lecture.interfaces.api.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RegistrationFacade {
    private final RegistrationService registrationService;
    private final LectureService lectureService;

    @Transactional
    public void register(RegistrationCommand registrationCommand) {
        // 유저 수강 신청 여부
        if (registrationService.checkRegistration(registrationCommand)) throw new RuntimeException("이미 신청 완료 된 특강입니다.");

        //강의 정보 조회
        LectureInfo lectureInfo = lectureService.read(registrationCommand.getLectureId());
        LectureCapacityInfo capacityInfo = lectureService.readCapacity(registrationCommand.getLectureId());
        LectureFullInfo lectureFullInfo = LectureFullInfo.builder()
                .lectureId(lectureInfo.getId())
                .lectureName(lectureInfo.getLectureName())
                .lectureDateTime(lectureInfo.getLectureDateTime())
                .instructor(lectureInfo.getInstructor())
                .instructorId(lectureInfo.getInstructorId())
                .capacity(capacityInfo.getCapacity())
                .currentEnrollment(capacityInfo.getCurrentEnrollment())
                .build();

        //수강생 초과 여부
        if (lectureFullInfo.getCurrentEnrollment() >= lectureFullInfo.getCapacity()) throw new RuntimeException("신청 불가: 강의 정원이 가득 찼습니다.");

        registrationService.register(registrationCommand);
        lectureService.incrementEnrollment(registrationCommand.getLectureId());
    }

}
