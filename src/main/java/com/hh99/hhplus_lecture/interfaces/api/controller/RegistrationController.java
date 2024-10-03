package com.hh99.hhplus_lecture.interfaces.api.controller;

import com.hh99.hhplus_lecture.application.facade.RegistrationFacade;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.interfaces.api.request.RegisterRequest;
import com.hh99.hhplus_lecture.interfaces.api.response.RegisteredLectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationFacade registrationFacade;

    /**
     * TODO - 특강신청 기능
     */
    @PostMapping()
    public HttpStatus register(@RequestBody RegisterRequest registerRequest) {
        registrationFacade.register(new RegistrationCommand(registerRequest.getUserId(), registerRequest.getLectureId()));
    return HttpStatus.CREATED;
    }

    /*
     * TODO - 특정 userId 로 신청 완료된 특강 목록을 조회하는 API 를 작성
     *      - 각 항목은 특강 ID 및 이름, 강연자 정보를 담고 있어야 합니다.
     */
    @GetMapping("/user/{userId}")
    public List<RegisteredLectureResponse> registeredLectures(@PathVariable String userId) {
        return registrationFacade.registeredLectures(userId);
    }
}
