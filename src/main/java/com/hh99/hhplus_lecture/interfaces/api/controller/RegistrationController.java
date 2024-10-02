package com.hh99.hhplus_lecture.interfaces.api.controller;

import com.hh99.hhplus_lecture.application.facade.RegistrationFacade;
import com.hh99.hhplus_lecture.interfaces.api.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationFacade registrationFacade;

    /**
     * TODO - 특강신청 기능
     */
    @PostMapping("")
    public HttpStatus register(@RequestBody RegisterRequest registerRequest) {
        registrationFacade.register(registerRequest);
    return HttpStatus.CREATED;
    }
}
