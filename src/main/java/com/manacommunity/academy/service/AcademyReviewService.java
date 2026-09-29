package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.AcademyInstructorEntity;
import com.manacommunity.academy.domain.entity.AcademyProgramEntity;
import com.manacommunity.academy.domain.entity.AcademyReviewEntity;
import com.manacommunity.academy.dto.ReviewResponseDto;
import com.manacommunity.academy.dto.SubmitReviewRequest;
import com.manacommunity.academy.repository.AcademyInstructorRepository;
import com.manacommunity.academy.repository.AcademyProgramRepository;
import com.manacommunity.academy.repository.AcademyReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademyReviewService {

    private final AcademyReviewRepository reviewRepository;
    private final AcademyProgramRepository programRepository;
    private final AcademyInstructorRepository instructorRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsForProgram(String programId) {
        return reviewRepository.findByProgramId(programId).stream()
                .map(ReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsForInstructor(String instructorId) {
        return reviewRepository.findByInstructorId(instructorId).stream()
                .map(ReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDto submitReview(String programId, SubmitReviewRequest req) {
        AcademyProgramEntity program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found: " + programId));

        Optional<AcademyReviewEntity> existing = reviewRepository.findByProgramIdAndUserId(programId, req.getUserId());
        AcademyReviewEntity review;
        if (existing.isPresent()) {
            review = existing.get();
            review.setOverallRating(req.getOverallRating());
            review.setInstructorRating(req.getInstructorRating());
            review.setContentRating(req.getContentRating());
            review.setReviewComment(req.getReviewComment());
            review.setWouldRecommend(req.getWouldRecommend() != null ? req.getWouldRecommend() : true);
        } else {
            review = AcademyReviewEntity.builder()
                    .id("rev-" + UUID.randomUUID().toString().substring(0, 8))
                    .programId(programId)
                    .instructorId(program.getInstructorId())
                    .userId(req.getUserId())
                    .userName(req.getUserName())
                    .overallRating(req.getOverallRating())
                    .instructorRating(req.getInstructorRating() != null ? req.getInstructorRating() : req.getOverallRating())
                    .contentRating(req.getContentRating() != null ? req.getContentRating() : req.getOverallRating())
                    .reviewComment(req.getReviewComment())
                    .wouldRecommend(req.getWouldRecommend() != null ? req.getWouldRecommend() : true)
                    .build();
        }

        AcademyReviewEntity saved = reviewRepository.save(review);

        // Recalculate average program rating
        List<AcademyReviewEntity> progReviews = reviewRepository.findByProgramId(programId);
        double avg = progReviews.stream().mapToInt(AcademyReviewEntity::getOverallRating).average().orElse(5.0);
        program.setAverageRating(Math.round(avg * 10.0) / 10.0);
        program.setReviewCount(progReviews.size());
        programRepository.save(program);

        // Recalculate average instructor rating
        instructorRepository.findById(program.getInstructorId()).ifPresent(inst -> {
            List<AcademyReviewEntity> instReviews = reviewRepository.findByInstructorId(inst.getId());
            double iAvg = instReviews.stream().mapToInt(AcademyReviewEntity::getOverallRating).average().orElse(5.0);
            inst.setAverageRating(Math.round(iAvg * 10.0) / 10.0);
            inst.setReviewCount(instReviews.size());
            instructorRepository.save(inst);
        });

        return ReviewResponseDto.fromEntity(saved);
    }
}
