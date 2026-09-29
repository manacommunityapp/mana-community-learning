package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.*;
import com.manacommunity.academy.domain.enums.EnrollmentStatus;
import com.manacommunity.academy.domain.enums.ProgramStatus;
import com.manacommunity.academy.dto.EnrollProgramRequest;
import com.manacommunity.academy.dto.EnrollmentResponseDto;
import com.manacommunity.academy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademyEnrollmentService {

    private final AcademyProgramRepository programRepository;
    private final AcademyEnrollmentRepository enrollmentRepository;
    private final AcademyWaitlistRepository waitlistRepository;
    private final AcademyInstructorRepository instructorRepository;

    @Transactional
    public EnrollmentResponseDto enrollUser(String programId, EnrollProgramRequest req) {
        // Concurrency-safe atomic capacity reservation with pessimistic lock
        AcademyProgramEntity program = programRepository.findByIdWithPessimisticLock(programId)
                .orElseThrow(() -> new IllegalArgumentException("Academy program not found with ID: " + programId));

        // Check if user already enrolled
        Optional<AcademyEnrollmentEntity> existing = enrollmentRepository.findByProgramIdAndUserId(programId, req.getUserId());
        if (existing.isPresent()) {
            if (existing.get().getStatus() == EnrollmentStatus.CONFIRMED) {
                log.info("User {} already enrolled in program {}", req.getUserId(), programId);
                return EnrollmentResponseDto.fromEntity(existing.get());
            } else if (existing.get().getStatus() == EnrollmentStatus.CANCELLED) {
                // Re-enroll
                if (program.isFull()) {
                    throw new IllegalStateException("Program is at full capacity (" + program.getCapacity() + " seats). Please join the waitlist.");
                }
                AcademyEnrollmentEntity e = existing.get();
                e.setStatus(EnrollmentStatus.CONFIRMED);
                e.setSeatNumber(program.getEnrolledCount() + 1);
                e.setEnrolledAt(LocalDateTime.now());
                program.setEnrolledCount(program.getEnrolledCount() + 1);
                if (program.isFull()) {
                    program.setStatus(ProgramStatus.FULL);
                }
                programRepository.save(program);
                return EnrollmentResponseDto.fromEntity(enrollmentRepository.save(e));
            }
        }

        // Check Capacity
        if (program.isFull()) {
            throw new IllegalStateException("Program is at full capacity (" + program.getCapacity() + " seats). Please join the waitlist.");
        }

        int newSeatNumber = program.getEnrolledCount() + 1;
        program.setEnrolledCount(newSeatNumber);
        if (program.isFull()) {
            program.setStatus(ProgramStatus.FULL);
        }
        programRepository.save(program);

        // Update Instructor Learners count
        instructorRepository.findById(program.getInstructorId()).ifPresent(inst -> {
            inst.setTotalLearners(inst.getTotalLearners() + 1);
            instructorRepository.save(inst);
        });

        String qrPassCode = "ACAD-PASS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        AcademyEnrollmentEntity enrollment = AcademyEnrollmentEntity.builder()
                .id("enroll-" + UUID.randomUUID().toString().substring(0, 8))
                .programId(program.getId())
                .programTitle(program.getTitle())
                .userId(req.getUserId())
                .userName(req.getUserName())
                .userEmail(req.getUserEmail())
                .userPhone(req.getUserPhone())
                .tower(req.getTower())
                .flatNumber(req.getFlatNumber())
                .seatNumber(newSeatNumber)
                .status(EnrollmentStatus.CONFIRMED)
                .amountPaid(req.getAmountPaid() != null ? req.getAmountPaid() : BigDecimal.ZERO)
                .paymentId(req.getPaymentId())
                .qrPassCode(qrPassCode)
                .enrolledAt(LocalDateTime.now())
                .build();

        AcademyEnrollmentEntity saved = enrollmentRepository.save(enrollment);
        log.info("Successfully enrolled user {} in program {} (Seat #{})", req.getUserName(), program.getTitle(), newSeatNumber);
        return EnrollmentResponseDto.fromEntity(saved);
    }

    @Transactional
    public void cancelEnrollment(String programId, String userId) {
        AcademyEnrollmentEntity enrollment = enrollmentRepository.findByProgramIdAndUserId(programId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found for program: " + programId + " and user: " + userId));

        if (enrollment.getStatus() == EnrollmentStatus.CONFIRMED) {
            enrollment.setStatus(EnrollmentStatus.CANCELLED);
            enrollment.setCancelledAt(LocalDateTime.now());
            enrollmentRepository.save(enrollment);

            AcademyProgramEntity program = programRepository.findByIdWithPessimisticLock(programId).orElse(null);
            if (program != null) {
                program.setEnrolledCount(Math.max(0, program.getEnrolledCount() - 1));
                if (program.getStatus() == ProgramStatus.FULL) {
                    program.setStatus(ProgramStatus.PUBLISHED);
                }
                programRepository.save(program);

                // Auto-promote first eligible waitlist resident
                waitlistRepository.findFirstByProgramIdAndClaimedFalseOrderByPositionAsc(programId).ifPresent(wl -> {
                    wl.setNotified(true);
                    wl.setNotifiedAt(LocalDateTime.now());
                    waitlistRepository.save(wl);
                    log.info("Notified waitlist resident {} for available seat in {}", wl.getUserName(), program.getTitle());
                });
            }
        }
    }

    @Transactional
    public AcademyWaitlistEntity joinWaitlist(String programId, String userId, String userName) {
        Optional<AcademyWaitlistEntity> existing = waitlistRepository.findByProgramIdAndUserId(programId, userId);
        if (existing.isPresent()) {
            return existing.get();
        }

        int currentCount = waitlistRepository.countByProgramId(programId);
        AcademyWaitlistEntity wl = AcademyWaitlistEntity.builder()
                .id("wl-" + UUID.randomUUID().toString().substring(0, 8))
                .programId(programId)
                .userId(userId)
                .userName(userName)
                .position(currentCount + 1)
                .notified(false)
                .claimed(false)
                .build();

        programRepository.findById(programId).ifPresent(p -> {
            p.setWaitlistCount(p.getWaitlistCount() + 1);
            programRepository.save(p);
        });

        return waitlistRepository.save(wl);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> getEnrollmentsForUser(String userId) {
        return enrollmentRepository.findByUserId(userId).stream()
                .map(EnrollmentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> getEnrollmentsForProgram(String programId) {
        return enrollmentRepository.findByProgramId(programId).stream()
                .map(EnrollmentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
