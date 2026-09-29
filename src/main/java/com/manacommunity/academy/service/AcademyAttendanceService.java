package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.AcademyAttendanceEntity;
import com.manacommunity.academy.domain.entity.AcademyProgramSessionEntity;
import com.manacommunity.academy.domain.enums.AttendanceStatus;
import com.manacommunity.academy.dto.AttendanceResponseDto;
import com.manacommunity.academy.dto.MarkAttendanceRequest;
import com.manacommunity.academy.repository.AcademyAttendanceRepository;
import com.manacommunity.academy.repository.AcademyProgramSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademyAttendanceService {

    private final AcademyAttendanceRepository attendanceRepository;
    private final AcademyProgramSessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getSessionAttendance(String sessionId) {
        return attendanceRepository.findBySessionId(sessionId).stream()
                .map(AttendanceResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AttendanceResponseDto markAttendance(String sessionId, MarkAttendanceRequest req) {
        AcademyProgramSessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + sessionId));

        Optional<AcademyAttendanceEntity> existing = attendanceRepository.findBySessionIdAndUserId(sessionId, req.getUserId());
        if (existing.isPresent()) {
            AcademyAttendanceEntity att = existing.get();
            att.setStatus(req.getStatus() != null ? req.getStatus() : AttendanceStatus.PRESENT);
            att.setCheckInTime(LocalDateTime.now());
            att.setCheckInMethod(req.getCheckInMethod() != null ? req.getCheckInMethod() : "MANUAL_INSTRUCTOR");
            att.setMarkedBy(req.getMarkedBy());
            return AttendanceResponseDto.fromEntity(attendanceRepository.save(att));
        }

        AcademyAttendanceEntity att = AcademyAttendanceEntity.builder()
                .id("att-" + UUID.randomUUID().toString().substring(0, 8))
                .sessionId(sessionId)
                .programId(session.getProgram() != null ? session.getProgram().getId() : "prog-unknown")
                .userId(req.getUserId())
                .userName(req.getUserName())
                .tower(req.getTower())
                .flatNumber(req.getFlatNumber())
                .status(req.getStatus() != null ? req.getStatus() : AttendanceStatus.PRESENT)
                .checkInTime(LocalDateTime.now())
                .checkInMethod(req.getCheckInMethod() != null ? req.getCheckInMethod() : "QR_SCAN")
                .markedBy(req.getMarkedBy())
                .build();

        return AttendanceResponseDto.fromEntity(attendanceRepository.save(att));
    }
}
