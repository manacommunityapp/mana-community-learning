package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyProgramEntity;
import com.manacommunity.academy.domain.enums.LearningType;
import com.manacommunity.academy.domain.enums.ProgramStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyProgramRepository extends JpaRepository<AcademyProgramEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM AcademyProgramEntity p WHERE p.id = :id")
    Optional<AcademyProgramEntity> findByIdWithPessimisticLock(@Param("id") String id);

    List<AcademyProgramEntity> findByCommunityIdAndStatus(String communityId, ProgramStatus status);

    List<AcademyProgramEntity> findByCommunityId(String communityId);

    List<AcademyProgramEntity> findByCommunityIdAndCategoryId(String communityId, String categoryId);

    List<AcademyProgramEntity> findByInstructorId(String instructorId);

    List<AcademyProgramEntity> findByLearningType(LearningType learningType);

    @Query("SELECT p FROM AcademyProgramEntity p WHERE p.communityId = :communityId AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.tags) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<AcademyProgramEntity> searchPrograms(@Param("communityId") String communityId, @Param("query") String query);
}
