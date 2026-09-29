package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.enums.LearningType;
import com.manacommunity.academy.domain.enums.ProgramStatus;
import com.manacommunity.academy.dto.CreateProgramRequest;
import com.manacommunity.academy.dto.ProgramResponseDto;
import com.manacommunity.academy.service.AcademyProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academy/programs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyProgramController {

    private final AcademyProgramService programService;

    @GetMapping
    public ResponseEntity<List<ProgramResponseDto>> getPrograms(
            @RequestParam(defaultValue = "comm-mana-1") String communityId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) LearningType learningType,
            @RequestParam(required = false) ProgramStatus status
    ) {
        return ResponseEntity.ok(programService.getPrograms(communityId, categoryId, learningType, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponseDto> getProgramById(@PathVariable String id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgramResponseDto>> searchPrograms(
            @RequestParam(defaultValue = "comm-mana-1") String communityId,
            @RequestParam(required = false) String q
    ) {
        return ResponseEntity.ok(programService.searchPrograms(communityId, q));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ProgramResponseDto>> getProgramsByInstructor(@PathVariable String instructorId) {
        return ResponseEntity.ok(programService.getProgramsByInstructor(instructorId));
    }

    @PostMapping
    public ResponseEntity<ProgramResponseDto> createProgram(@Valid @RequestBody CreateProgramRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createProgram(req));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProgramResponseDto> updateStatus(
            @PathVariable String id,
            @RequestParam ProgramStatus status
    ) {
        return ResponseEntity.ok(programService.updateStatus(id, status));
    }
}
