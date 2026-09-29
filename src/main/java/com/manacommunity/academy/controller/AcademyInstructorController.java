package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.enums.InstructorStatus;
import com.manacommunity.academy.dto.InstructorApplicationRequest;
import com.manacommunity.academy.dto.InstructorResponseDto;
import com.manacommunity.academy.service.AcademyInstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academy/instructors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyInstructorController {

    private final AcademyInstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<InstructorResponseDto>> getInstructors(
            @RequestParam(defaultValue = "comm-mana-1") String communityId
    ) {
        return ResponseEntity.ok(instructorService.getApprovedInstructors(communityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponseDto> getInstructorById(@PathVariable String id) {
        return ResponseEntity.ok(instructorService.getInstructorById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<InstructorResponseDto> getInstructorByUserId(@PathVariable String userId) {
        return instructorService.getInstructorByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/apply")
    public ResponseEntity<InstructorResponseDto> applyAsInstructor(
            @Valid @RequestBody InstructorApplicationRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.applyAsInstructor(req));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InstructorResponseDto> updateStatus(
            @PathVariable String id,
            @RequestParam InstructorStatus status,
            @RequestParam(defaultValue = "admin") String adminUserId
    ) {
        return ResponseEntity.ok(instructorService.updateStatus(id, status, adminUserId));
    }
}
