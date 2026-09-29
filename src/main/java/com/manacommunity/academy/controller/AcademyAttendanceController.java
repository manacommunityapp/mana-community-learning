package com.manacommunity.academy.controller;

import com.manacommunity.academy.dto.AttendanceResponseDto;
import com.manacommunity.academy.dto.MarkAttendanceRequest;
import com.manacommunity.academy.service.AcademyAttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academy/sessions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyAttendanceController {

    private final AcademyAttendanceService attendanceService;

    @GetMapping("/{sessionId}/attendance")
    public ResponseEntity<List<AttendanceResponseDto>> getSessionAttendance(@PathVariable String sessionId) {
        return ResponseEntity.ok(attendanceService.getSessionAttendance(sessionId));
    }

    @PostMapping("/{sessionId}/attendance")
    public ResponseEntity<AttendanceResponseDto> markAttendance(
            @PathVariable String sessionId,
            @Valid @RequestBody MarkAttendanceRequest req
    ) {
        return ResponseEntity.ok(attendanceService.markAttendance(sessionId, req));
    }
}
