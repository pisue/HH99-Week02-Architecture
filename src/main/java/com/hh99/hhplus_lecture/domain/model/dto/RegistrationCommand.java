package com.hh99.hhplus_lecture.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationCommand {
    private String userId;
    private Long lectureId;
}
