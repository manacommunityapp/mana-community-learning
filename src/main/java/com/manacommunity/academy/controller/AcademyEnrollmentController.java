package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.entity.AcademyWaitlistEntity;
import com.manacommunity.academy.dto.EnrollProgramRequest;
import com.manacommunity.academy.dto.EnrollmentResponseDto;
import com.manacommunity.academy.service.AcademyEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academy/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyEnrollmentController {

    private final AcademyEnrollmentService enrollmentService;

    @PostMapping("/program/{programId}")
    public ResponseEntity<EnrollmentResponseDto> enroll(
            @PathVariable String programId,
            @Valid @RequestBody EnrollProgramRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollUser(programId, req));
    }

    @DeleteMapping("/program/{programId}/user/{userId}")
    public ResponseEntity<Void> cancelEnrollment(
            @PathVariable String programId,
            @PathVariable String userId
    ) {
        enrollmentService.cancelEnrollment(programId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/program/{programId}/waitlist")
    public ResponseEntity<AcademyWaitlistEntity> joinWaitlist(
            @PathVariable String programId,
            @RequestParam String userId,
            @RequestParam String userName
    ) {
        return ResponseEntity.ok(enrollmentService.joinWaitlist(programId, userId, userName));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsForUser(@PathVariable String userId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsForUser(userId));
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsForProgram(@PathVariable String programId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsForProgram(programId));
    }
}
