package com.hh99.hhplus_lecture.interfaces.api.controller;

import com.hh99.hhplus_lecture.application.facade.LectureFacade;
import com.hh99.hhplus_lecture.interfaces.api.response.LectureInfosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {
    private final LectureFacade lectureFacade;

    @GetMapping("")
    @ResponseBody
    public LectureInfosResponse lectures() {
        return lectureFacade.lectureInfos();
    }

}
