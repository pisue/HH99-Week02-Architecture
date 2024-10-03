package com.hh99.hhplus_lecture.domain.service;

import com.hh99.hhplus_lecture.application.model.dto.RegisteredInfo;
import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;
    private RegistrationCommand registrationCommand;
    private List<RegisteredInfo> registeredInfoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationCommand = new RegistrationCommand("testId", 1L);
        RegisteredInfo registeredInfo1 = RegisteredInfo.builder()
                .registrationDate(LocalDateTime.now())
                .userId("testId")
                .lectureId(1L)
                .build();
        RegisteredInfo registeredInfo2 = RegisteredInfo.builder()
                .registrationDate(LocalDateTime.now())
                .userId("testId")
                .lectureId(2L)
                .build();
        RegisteredInfo registeredInfo3 = RegisteredInfo.builder()
                .registrationDate(LocalDateTime.now())
                .userId("testId")
                .lectureId(2L)
                .build();
        registeredInfoList = List.of(registeredInfo1, registeredInfo2, registeredInfo3);
    }

    @Test
    void 특강_신청() {
        //given

        //when
        registrationRepository.register(registrationCommand);

        //then
        verify(registrationRepository, times(1)).register(registrationCommand);
    }

    @Test
    void 수강신청_여부_확인() {
        //given
        when(registrationRepository.checkRegistration(registrationCommand)).thenReturn(true);

        //when
        boolean result = registrationService.checkRegistration(registrationCommand);

        //then
        assertTrue(result);
        verify(registrationRepository, times(1)).checkRegistration(registrationCommand);
    }

    @Test
    void 신청_내역_없으면_false_반환() {
        //given
        when(registrationRepository.checkRegistration(registrationCommand)).thenReturn(false);

        //when
        boolean result = registrationService.checkRegistration(registrationCommand);

        //then
        assertFalse(result);
        verify(registrationRepository, times(1)).checkRegistration(registrationCommand);
    }

    @Test
    void 유저가_신청한_강의_목록_조회() {
        //given
        when(registrationRepository.findByUserId("testId")).thenReturn(registeredInfoList);

        //when
        List<RegisteredInfo> result = registrationRepository.findByUserId("testId");

        //then
        verify(registrationRepository, times(1)).findByUserId("testId");
        assertEquals(registeredInfoList.size(), result.size());
        assertEquals(registeredInfoList, result);
    }


}