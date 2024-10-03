package com.hh99.hhplus_lecture.domain.service;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureInfo read(Long id){
        return lectureRepository.read(id);
    }

    public LectureCapacityInfo readCapacity(Long lectureId){
        return lectureRepository.readCapacity(lectureId);
    }

    public void incrementEnrollment(Long lectureId) {
        lectureRepository.incrementEnrollment(lectureId);
    }


    public List<LectureInfo> lectures() {
        return lectureRepository.lectures();
    }

    public List<LectureCapacityInfo> capacities() {
        return lectureRepository.capacities();
    }
}

