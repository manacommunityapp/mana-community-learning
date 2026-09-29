package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyProgramSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyProgramSessionRepository extends JpaRepository<AcademyProgramSessionEntity, String> {
    List<AcademyProgramSessionEntity> findByProgramIdOrderBySessionOrderAsc(String programId);
    Optional<AcademyProgramSessionEntity> findByQrCheckInToken(String qrCheckInToken);
}
