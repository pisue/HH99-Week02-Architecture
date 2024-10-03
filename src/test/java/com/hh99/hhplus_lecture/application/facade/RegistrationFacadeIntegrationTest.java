package com.hh99.hhplus_lecture.application.facade;

import com.hh99.hhplus_lecture.domain.model.dto.RegistrationCommand;
import com.hh99.hhplus_lecture.domain.service.LectureService;
import com.hh99.hhplus_lecture.infrastructure.model.entity.Lecture;
import com.hh99.hhplus_lecture.infrastructure.model.entity.LectureCapacity;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureCapacityJpaRepository;
import com.hh99.hhplus_lecture.infrastructure.repository.LectureJpaRepository;
import com.hh99.hhplus_lecture.infrastructure.repository.impl.RegistrationJpaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RegistrationFacadeIntegrationTest {

    @Autowired
    private RegistrationFacade registrationFacade;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureCapacityJpaRepository lectureCapacityJpaRepository;

    private Lecture lecture;
    private LectureCapacity lectureCapacity;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private RegistrationJpaRepositoryImpl registrationJpaRepositoryImpl;

    @BeforeEach
    void setUp() {
        lecture = new Lecture();
        lecture.setLectureName("Test Lecture");
        lecture.setInstructor("Test Instructor");
        lecture.setInstructorId(1L);
        lecture.setLectureDateTime(LocalDateTime.now());
        lecture = lectureJpaRepository.save(lecture);

        lectureCapacity = new LectureCapacity();
        lectureCapacity.setLectureId(lecture.getId());
        lectureCapacity.setCapacity(30);
        lectureCapacity.setCurrentEnrollment(0);
        lectureCapacity = lectureCapacityJpaRepository.save(lectureCapacity);
    }

    @Test
    void 수강신청_동시성_테스트() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final String userId = "user" + i;
            executorService.execute(() -> {
                try {
                    registrationFacade.register(new RegistrationCommand(userId, lecture.getId()));
                } catch (ObjectOptimisticLockingFailureException e) {
                    // 예상된 예외, 무시
                } catch (RuntimeException e) {
                    if (!e.getMessage().equals("신청 불가: 강의 정원이 가득 찼습니다.")) {
                        throw e;
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        LectureCapacity updatedCapacity = lectureCapacityJpaRepository.findByLectureId(lecture.getId());
        assertEquals(10, updatedCapacity.getCurrentEnrollment());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void 수강신청_동시성_테스트_수강인원을_초과했을_때() throws InterruptedException {
        int numberOfThreads = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final String userId = "user" + i;
            executorService.execute(() -> {
                try {
                    registrationFacade.register(new RegistrationCommand(userId, lecture.getId()));
                } catch (ObjectOptimisticLockingFailureException e) {
                    // 예상된 예외, 무시
                } catch (RuntimeException e) {
                    if (e.getMessage().equals("신청 불가: 강의 정원이 가득 찼습니다.")) {
                        failCount.incrementAndGet();
                    }else {
                        throw e;
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        LectureCapacity updatedCapacity = transactionTemplate.execute(status ->
                lectureCapacityJpaRepository.findByLectureId(lecture.getId())
        );
        assertEquals(30, updatedCapacity.getCurrentEnrollment());
        assertEquals(10, failCount.get());

        System.out.println("성공한 수강신청 수: " + updatedCapacity.getCurrentEnrollment());
        System.out.println("실패한 수강신청 수: " + failCount.get());
    }

    @Test
    void 동일사용자_중복신청_테스트() {
        String userId = "testUser2";
        int numberOfAttempts = 5;
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfAttempts; i++) {
            try {
                RegistrationCommand command = new RegistrationCommand(userId, lecture.getId());
                registrationFacade.register(command);
                successCount.incrementAndGet();
            } catch (RuntimeException e) {
                if (e.getMessage().equals("이미 신청 완료 된 특강입니다.")) {
                    failCount.incrementAndGet();
                } else {
                    throw e;
                }
            }
        }

        assertEquals(1, successCount.get(), "성공한 신청 횟수는 1이어야 합니다.");
        assertEquals(4, failCount.get(), "실패한 신청 횟수는 4여야 합니다.");

        LectureCapacity updatedCapacity = lectureCapacityJpaRepository.findByLectureId(lecture.getId());
        assertEquals(1, updatedCapacity.getCurrentEnrollment(), "현재 등록 인원은 1이어야 합니다.");
    }

}