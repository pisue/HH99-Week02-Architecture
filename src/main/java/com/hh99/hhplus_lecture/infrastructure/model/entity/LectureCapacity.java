package com.hh99.hhplus_lecture.infrastructure.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class LectureCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long lectureId;

    private int capacity;

    private int currentEnrollment;
}
