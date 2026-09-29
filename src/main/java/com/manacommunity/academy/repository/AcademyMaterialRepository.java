package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyMaterialRepository extends JpaRepository<AcademyMaterialEntity, String> {
    List<AcademyMaterialEntity> findByProgramId(String programId);
    List<AcademyMaterialEntity> findBySessionId(String sessionId);
}
