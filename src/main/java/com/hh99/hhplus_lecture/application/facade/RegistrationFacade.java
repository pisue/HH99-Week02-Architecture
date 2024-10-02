package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.service.RegistrationService;
import com.hh99.hhplus_lecture.interfaces.api.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RegistrationFacade {
    private final RegistrationService registrationService;
    private final LectureFacade lectureFacade;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        if (registrationService.checkRegistration(registerRequest)) {
            throw new RuntimeException("이미 신청 완료 된 특강입니다.");
        }

        LectureFullInfo lectureFullInfo = lectureFacade.getLectureFullInfo(registerRequest.getLectureId());

        if (lectureFullInfo.getCurrentEnrollment() >= lectureFullInfo.getCapacity()) {
            throw new RuntimeException("신청 불가: 강의 정원이 가득 찼습니다.");
        }

        registrationService.register(new RegistrationCommand(registerRequest.getUserId(), lectureFullInfo.getLectureId()));
        lectureFacade.incrementEnrollment(registerRequest.getLectureId());
    }

}
