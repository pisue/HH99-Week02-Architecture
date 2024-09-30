package com.hh99.hhplus_lecture.domain.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String instructor;
    private LocalDateTime startTime;
    private LocalDateTime endT1ime;

    @Column(nullable = false, columnDefinition = "int default 30")
    private Integer maxAttendees = 30;
    private Integer currentAttendees = maxAttendees;

    @Version
    private Long version;
}
