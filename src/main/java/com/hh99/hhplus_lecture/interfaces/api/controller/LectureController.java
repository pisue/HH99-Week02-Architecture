package com.hh99.hhplus_lecture.interfaces.api.controller;

import com.hh99.hhplus_lecture.application.facade.LectureFacade;
import com.hh99.hhplus_lecture.interfaces.api.response.LectureInfosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {
    private final LectureFacade lectureFacade;

    /**
     * TODO - 전체 강의 조회
     */
    @GetMapping("")
    @ResponseBody
    public LectureInfosResponse lectures() {
        return lectureFacade.lectureInfos();
    }

    /*
    * TODO - 날짜별로 현재 신청 가능한 특강 목록을 조회하는 API
    */
    @GetMapping("{date}")
    @ResponseBody
    public LectureInfosResponse getLecturesAfterDate(@PathVariable LocalDate date) {
        return lectureFacade.getLecturesAfterDate(date);
    }
}
