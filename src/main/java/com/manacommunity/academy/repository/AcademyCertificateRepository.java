package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyCertificateRepository extends JpaRepository<AcademyCertificateEntity, String> {
    List<AcademyCertificateEntity> findByUserId(String userId);
    Optional<AcademyCertificateEntity> findByProgramIdAndUserId(String programId, String userId);
    Optional<AcademyCertificateEntity> findByCertificateNumber(String certificateNumber);
}
