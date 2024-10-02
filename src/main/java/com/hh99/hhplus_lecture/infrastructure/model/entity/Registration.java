package com.hh99.hhplus_lecture.infrastructure.model.entity;

import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration", uniqueConstraints = {
        // 데이터 무결성 보장 (사용할지 안할지 고민중)
        @UniqueConstraint(columnNames = {"userId", "lectureId"})
})
@Data
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Long lectureId;

    private LocalDateTime registrationDate;

    public Registration(RegistrationCommand registrationCommand) {
        this.userId = registrationCommand.getUserId();
        this.lectureId = registrationCommand.getLectureId();
        this.registrationDate = LocalDateTime.now();
    }
}
