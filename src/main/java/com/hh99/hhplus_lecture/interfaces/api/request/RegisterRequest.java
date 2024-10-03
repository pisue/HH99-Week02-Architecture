package com.hh99.hhplus_lecture.interfaces.api.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userId;
    private Long lectureId;
}
