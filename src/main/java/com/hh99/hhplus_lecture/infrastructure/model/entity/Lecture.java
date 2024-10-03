package com.hh99.hhplus_lecture.infrastructure.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture")
@Data
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private Long instructorId;

    @Column(nullable = false)
    private LocalDateTime lectureDateTime;
}
