package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.enums.InstructorStatus;
import com.manacommunity.academy.domain.enums.ProgramStatus;
import com.manacommunity.academy.dto.InstructorResponseDto;
import com.manacommunity.academy.dto.ProgramResponseDto;
import com.manacommunity.academy.service.AcademyInstructorService;
import com.manacommunity.academy.service.AcademyProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/academy/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyAdminController {

    private final AcademyInstructorService instructorService;
    private final AcademyProgramService programService;

    @GetMapping("/dashboard-summary")
    public ResponseEntity<Map<String, Object>> getAdminSummary(
            @RequestParam(defaultValue = "comm-mana-1") String communityId
    ) {
        List<ProgramResponseDto> allPrograms = programService.getPrograms(communityId, null, null, null);
        List<InstructorResponseDto> allInstructors = instructorService.getAllInstructors(communityId);

        long totalPrograms = allPrograms.size();
        long publishedPrograms = allPrograms.stream().filter(p -> p.getStatus() == ProgramStatus.PUBLISHED || p.getStatus() == ProgramStatus.REGISTRATION_OPEN).count();
        long totalEnrolledLearners = allPrograms.stream().mapToInt(ProgramResponseDto::getEnrolledCount).sum();
        long pendingInstructors = allInstructors.stream().filter(i -> i.getStatus() == InstructorStatus.APPLIED).count();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalPrograms", totalPrograms);
        summary.put("activePrograms", publishedPrograms);
        summary.put("totalEnrolledLearners", totalEnrolledLearners);
        summary.put("totalInstructors", allInstructors.size());
        summary.put("pendingInstructorApprovals", pendingInstructors);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/instructors")
    public ResponseEntity<List<InstructorResponseDto>> getAllInstructors(
            @RequestParam(defaultValue = "comm-mana-1") String communityId
    ) {
        return ResponseEntity.ok(instructorService.getAllInstructors(communityId));
    }
}
