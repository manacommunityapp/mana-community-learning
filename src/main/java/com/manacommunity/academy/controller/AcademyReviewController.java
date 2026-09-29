package com.manacommunity.academy.controller;

import com.manacommunity.academy.dto.ReviewResponseDto;
import com.manacommunity.academy.dto.SubmitReviewRequest;
import com.manacommunity.academy.service.AcademyReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academy/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyReviewController {

    private final AcademyReviewService reviewService;

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsForProgram(@PathVariable String programId) {
        return ResponseEntity.ok(reviewService.getReviewsForProgram(programId));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsForInstructor(@PathVariable String instructorId) {
        return ResponseEntity.ok(reviewService.getReviewsForInstructor(instructorId));
    }

    @PostMapping("/program/{programId}")
    public ResponseEntity<ReviewResponseDto> submitReview(
            @PathVariable String programId,
            @Valid @RequestBody SubmitReviewRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.submitReview(programId, req));
    }
}
