package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.*;
import com.manacommunity.academy.domain.enums.*;
import com.manacommunity.academy.dto.CreateProgramRequest;
import com.manacommunity.academy.dto.CreateSessionRequest;
import com.manacommunity.academy.dto.ProgramResponseDto;
import com.manacommunity.academy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademyProgramService {

    private final AcademyProgramRepository programRepository;
    private final AcademyCategoryRepository categoryRepository;
    private final AcademyInstructorRepository instructorRepository;

    @Transactional(readOnly = true)
    public List<ProgramResponseDto> getPrograms(String communityId, String categoryId, LearningType learningType, ProgramStatus status) {
        List<AcademyProgramEntity> list;
        if (categoryId != null && !categoryId.isBlank()) {
            list = programRepository.findByCommunityIdAndCategoryId(communityId, categoryId);
        } else if (status != null) {
            list = programRepository.findByCommunityIdAndStatus(communityId, status);
        } else {
            list = programRepository.findByCommunityId(communityId);
        }

        if (learningType != null) {
            list = list.stream().filter(p -> p.getLearningType() == learningType).collect(Collectors.toList());
        }

        return list.stream().map(ProgramResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProgramResponseDto getProgramById(String id) {
        AcademyProgramEntity program = programRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academy program not found with ID: " + id));
        return ProgramResponseDto.fromEntity(program);
    }

    @Transactional(readOnly = true)
    public List<ProgramResponseDto> searchPrograms(String communityId, String query) {
        if (query == null || query.isBlank()) {
            return getPrograms(communityId, null, null, ProgramStatus.PUBLISHED);
        }
        return programRepository.searchPrograms(communityId, query.trim()).stream()
                .map(ProgramResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProgramResponseDto> getProgramsByInstructor(String instructorId) {
        return programRepository.findByInstructorId(instructorId).stream()
                .map(ProgramResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProgramResponseDto createProgram(CreateProgramRequest req) {
        AcademyCategoryEntity category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + req.getCategoryId()));

        AcademyInstructorEntity instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found: " + req.getInstructorId()));

        String programId = "prog-" + UUID.randomUUID().toString().substring(0, 8);

        AcademyProgramEntity program = AcademyProgramEntity.builder()
                .id(programId)
                .communityId(req.getCommunityId())
                .instructorId(instructor.getId())
                .instructorName(instructor.getFullName())
                .categoryId(category.getId())
                .categoryName(category.getName())
                .title(req.getTitle())
                .summary(req.getSummary())
                .description(req.getDescription())
                .coverImageUrl(req.getCoverImageUrl())
                .learningType(req.getLearningType() != null ? req.getLearningType() : LearningType.WORKSHOP)
                .level(req.getLevel() != null ? req.getLevel() : ProgramLevel.ALL_LEVELS)
                .mode(req.getMode() != null ? req.getMode() : ProgramMode.IN_PERSON)
                .location(req.getLocation())
                .onlineMeetingUrl(req.getOnlineMeetingUrl())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .startTime(req.getStartTime())
                .durationMinutes(req.getDurationMinutes() != null ? req.getDurationMinutes() : 60)
                .capacity(req.getCapacity() != null ? req.getCapacity() : 20)
                .enrolledCount(0)
                .waitlistCount(0)
                .pricingType(req.getPricingType() != null ? req.getPricingType() : PricingType.FREE)
                .price(req.getPrice() != null ? req.getPrice() : BigDecimal.ZERO)
                .prerequisites(req.getPrerequisites())
                .targetAudience(req.getTargetAudience())
                .tags(req.getTags())
                .certificateEnabled(req.getCertificateEnabled() != null ? req.getCertificateEnabled() : false)
                .status(ProgramStatus.PUBLISHED)
                .averageRating(5.0)
                .reviewCount(0)
                .sessions(new ArrayList<>())
                .build();

        if (req.getSessions() != null && !req.getSessions().isEmpty()) {
            int order = 1;
            for (CreateSessionRequest sReq : req.getSessions()) {
                AcademyProgramSessionEntity session = AcademyProgramSessionEntity.builder()
                        .id("sess-" + UUID.randomUUID().toString().substring(0, 8))
                        .program(program)
                        .sessionOrder(sReq.getSessionOrder() != null ? sReq.getSessionOrder() : order++)
                        .title(sReq.getTitle())
                        .description(sReq.getDescription())
                        .sessionDate(sReq.getSessionDate() != null ? sReq.getSessionDate() : req.getStartDate())
                        .startTime(sReq.getStartTime() != null ? sReq.getStartTime() : req.getStartTime())
                        .endTime(sReq.getEndTime())
                        .location(sReq.getLocation() != null ? sReq.getLocation() : req.getLocation())
                        .meetingLink(sReq.getMeetingLink() != null ? sReq.getMeetingLink() : req.getOnlineMeetingUrl())
                        .qrCheckInToken("QR-SESS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                        .completed(false)
                        .build();
                program.getSessions().add(session);
            }
        } else {
            // Default single session for workshops
            AcademyProgramSessionEntity session = AcademyProgramSessionEntity.builder()
                    .id("sess-" + UUID.randomUUID().toString().substring(0, 8))
                    .program(program)
                    .sessionOrder(1)
                    .title(req.getTitle() + " - Session 1")
                    .description(req.getSummary())
                    .sessionDate(req.getStartDate())
                    .startTime(req.getStartTime())
                    .location(req.getLocation())
                    .meetingLink(req.getOnlineMeetingUrl())
                    .qrCheckInToken("QR-SESS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .completed(false)
                    .build();
            program.getSessions().add(session);
        }

        AcademyProgramEntity saved = programRepository.save(program);

        // Update instructor stats
        instructor.setTotalSessions(instructor.getTotalSessions() + saved.getSessions().size());
        instructorRepository.save(instructor);

        log.info("Created Academy Program: {} with ID: {}", saved.getTitle(), saved.getId());
        return ProgramResponseDto.fromEntity(saved);
    }

    @Transactional
    public ProgramResponseDto updateStatus(String id, ProgramStatus newStatus) {
        AcademyProgramEntity program = programRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with ID: " + id));
        program.setStatus(newStatus);
        return ProgramResponseDto.fromEntity(programRepository.save(program));
    }
}
