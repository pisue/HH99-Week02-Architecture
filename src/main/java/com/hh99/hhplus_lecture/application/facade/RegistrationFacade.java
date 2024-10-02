package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.service.LectureService;
import com.hh99.hhplus_lecture.domain.service.RegistrationService;
import com.hh99.hhplus_lecture.interfaces.api.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RegistrationFacade {
    @Lazy
    private final RegistrationService registrationService;
    private final LectureService lectureService;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        LectureInfo lectureInfo = lectureService.read(registerRequest.getLectureId());

        if (lectureInfo.getCurrentEnrollment() >= lectureInfo.getCapacity()) {
            throw new RuntimeException("신청 불가: 강의 정원이 가득 찼습니다.");
        }

        registrationService.register(new RegistrationCommand(registerRequest.getUserId(), lectureInfo.getLectureId()));
        lectureService.incrementEnrollment(registerRequest.getLectureId());
    }

}
