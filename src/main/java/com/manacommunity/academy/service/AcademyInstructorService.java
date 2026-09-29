package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.AcademyInstructorEntity;
import com.manacommunity.academy.domain.enums.InstructorStatus;
import com.manacommunity.academy.dto.InstructorApplicationRequest;
import com.manacommunity.academy.dto.InstructorResponseDto;
import com.manacommunity.academy.repository.AcademyInstructorRepository;
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
public class AcademyInstructorService {

    private final AcademyInstructorRepository instructorRepository;

    @Transactional(readOnly = true)
    public List<InstructorResponseDto> getApprovedInstructors(String communityId) {
        return instructorRepository.findByCommunityIdAndStatus(communityId, InstructorStatus.APPROVED).stream()
                .map(InstructorResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InstructorResponseDto> getAllInstructors(String communityId) {
        return instructorRepository.findByCommunityId(communityId).stream()
                .map(InstructorResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<InstructorResponseDto> getInstructorByUserId(String residentUserId) {
        return instructorRepository.findByResidentUserId(residentUserId)
                .map(InstructorResponseDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructorById(String id) {
        AcademyInstructorEntity instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + id));
        return InstructorResponseDto.fromEntity(instructor);
    }

    @Transactional
    public InstructorResponseDto applyAsInstructor(InstructorApplicationRequest req) {
        Optional<AcademyInstructorEntity> existing = instructorRepository.findByResidentUserId(req.getResidentUserId());
        if (existing.isPresent()) {
            AcademyInstructorEntity inst = existing.get();
            inst.setProfession(req.getProfession());
            inst.setBio(req.getBio());
            inst.setSkills(req.getSkills());
            inst.setExperienceYears(req.getExperienceYears());
            if (inst.getStatus() == InstructorStatus.REJECTED) {
                inst.setStatus(InstructorStatus.APPLIED);
            }
            return InstructorResponseDto.fromEntity(instructorRepository.save(inst));
        }

        AcademyInstructorEntity instructor = AcademyInstructorEntity.builder()
                .id("instr-" + UUID.randomUUID().toString().substring(0, 8))
                .communityId(req.getCommunityId())
                .residentUserId(req.getResidentUserId())
                .fullName(req.getFullName())
                .profession(req.getProfession())
                .bio(req.getBio())
                .profilePicUrl(req.getProfilePicUrl())
                .tower(req.getTower())
                .flatNumber(req.getFlatNumber())
                .skills(req.getSkills())
                .experienceYears(req.getExperienceYears())
                .status(InstructorStatus.APPROVED) // Auto-approve for verified community demo / admin reviewable
                .totalSessions(0)
                .totalLearners(0)
                .averageRating(5.0)
                .reviewCount(0)
                .approvedAt(LocalDateTime.now())
                .build();

        AcademyInstructorEntity saved = instructorRepository.save(instructor);
        log.info("Instructor registered: {} with ID: {}", saved.getFullName(), saved.getId());
        return InstructorResponseDto.fromEntity(saved);
    }

    @Transactional
    public InstructorResponseDto updateStatus(String instructorId, InstructorStatus status, String adminUserId) {
        AcademyInstructorEntity instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + instructorId));
        instructor.setStatus(status);
        if (status == InstructorStatus.APPROVED) {
            instructor.setApprovedBy(adminUserId);
            instructor.setApprovedAt(LocalDateTime.now());
        }
        return InstructorResponseDto.fromEntity(instructorRepository.save(instructor));
    }
}
