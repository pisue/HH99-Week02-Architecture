package com.hh99.hhplus_lecture.domain.service;

import com.hh99.hhplus_lecture.domain.model.dto.LectureCapacityInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureFullInfo;
import com.hh99.hhplus_lecture.domain.model.dto.LectureInfo;
import com.hh99.hhplus_lecture.domain.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;


    @InjectMocks
    private LectureService lectureService;

    private LectureInfo lectureInfo;
    private LectureInfo lectureInfo2;
    private LectureCapacityInfo lectureCapacityInfo;
    private LectureCapacityInfo lectureCapacityInfo2;
    private List<LectureInfo> lectureInfoList;
    private List<LectureCapacityInfo> lectureCapacityInfoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //LectureInfo
        lectureInfo= LectureInfo.builder()
                .id(1L)
                .lectureDateTime(LocalDateTime.now())
                .lectureName("자바 프로그래밍")
                .instructor("김강사")
                .instructorId(1L)
                .build();

        lectureInfo2 = LectureInfo.builder()
                .id(2L)
                .lectureDateTime(LocalDateTime.now().plusDays(1))
                .lectureName("스프링 부트")
                .instructor("이강사")
                .instructorId(2L)
                .build();

        lectureInfoList = List.of(lectureInfo,lectureInfo2);

        //LectureInfo
        lectureCapacityInfo = LectureCapacityInfo.builder()
                .lectureId(1L)
                .capacity(30)
                .currentEnrollment(15)
                .build();

        lectureCapacityInfo2 = LectureCapacityInfo.builder()
                .lectureId(2L)
                .capacity(25)
                .currentEnrollment(20)
                .build();

        lectureCapacityInfoList = List.of(lectureCapacityInfo,lectureCapacityInfo2);
    }

    @Test
    void 특강정보_조회() {
        //given
        when(lectureRepository.read(1L)).thenReturn(lectureInfo);

        //when
        LectureInfo result = lectureService.read(1L);

        //then
        assertNotNull(result);
        assertEquals(lectureInfo, result);
        verify(lectureRepository, times(1)).read(1L);
    }

    @Test
    void 특강정보_존재하지_않을_때() {
        //given
        when(lectureRepository.read(2L)).thenThrow(new RuntimeException("강의 정보가 존재하지 않습니다."));

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lectureService.read(2L));

        //then
        verify(lectureRepository, times(1)).read(2L);
        assertEquals("강의 정보가 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void 특강_수강인원_정보_조회() {
        //given
        when(lectureRepository.readCapacity(1L)).thenReturn(lectureCapacityInfo);

        //when
        LectureCapacityInfo result = lectureService.readCapacity(1L);

        //then
        assertNotNull(result);
        assertEquals(lectureCapacityInfo, result);
        verify(lectureRepository, times(1)).readCapacity(1L);
    }

    @Test
    void 특강_수강인원_정보가_존재하지_않을_때() {
        //given
        when(lectureRepository.readCapacity(2L)).thenThrow(new RuntimeException("특강 수강인원 정보가 존재하지 않습니다."));

        //when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lectureService.readCapacity(2L));

        //then
        verify(lectureRepository, times(1)).readCapacity(2L);
        assertEquals("특강 수강인원 정보가 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void 특강_신청_시_강의_신청인원_업데이트() {
        // given

        // when
        lectureService.incrementEnrollment(1L);

        // then
        verify(lectureRepository, times(1)).incrementEnrollment(1L);
    }

    @Test
    void 특강정보_리스트_조회() {
        //given
        when(lectureRepository.lectures()).thenReturn(lectureInfoList);

        //when
        List<LectureInfo> result = lectureService.lectures();

        //then
        assertEquals(lectureInfoList, result);
        assertEquals(2, lectureInfoList.size());
        verify(lectureRepository, times(1)).lectures();
    }

    @Test
    void 수강인원_정보_리스트_조회() {
        //given
        when(lectureRepository.capacities()).thenReturn(lectureCapacityInfoList);

        //when
        List<LectureCapacityInfo> result = lectureService.capacities();

        //then
        assertEquals(lectureCapacityInfoList, result);
        assertEquals(2, lectureCapacityInfoList.size());
        verify(lectureRepository, times(1)).capacities();
    }

    @Test
    void 날짜별_특강정보_조회() {
        //given
        LocalDate testDate = LocalDate.of(2024, 10, 1);
        LectureFullInfo LectureFullInfo1 = LectureFullInfo.builder()
                .lectureId(lectureInfo.getId())
                .lectureName(lectureInfo.getLectureName())
                .lectureDateTime(lectureInfo.getLectureDateTime())
                .instructor(lectureInfo.getInstructor())
                .instructorId(lectureInfo.getInstructorId())
                .capacity(lectureCapacityInfo.getCapacity())
                .currentEnrollment(lectureCapacityInfo.getCurrentEnrollment())
                .build();
        LectureFullInfo LectureFullInfo2 = LectureFullInfo.builder()
                .lectureId(lectureInfo.getId())
                .lectureName(lectureInfo.getLectureName())
                .lectureDateTime(lectureInfo.getLectureDateTime())
                .instructor(lectureInfo.getInstructor())
                .instructorId(lectureInfo.getInstructorId())
                .capacity(lectureCapacityInfo.getCapacity())
                .currentEnrollment(lectureCapacityInfo.getCurrentEnrollment())
                .build();
        when(lectureRepository.findLecturesAfterDate(testDate)).thenReturn(List.of(LectureFullInfo1,LectureFullInfo2));

        //when
        List<LectureFullInfo> result = lectureService.findLecturesAfterDate(testDate);
        assertEquals(List.of(LectureFullInfo1,LectureFullInfo2), result);
        verify(lectureRepository, times(1)).findLecturesAfterDate(testDate);
    }

}