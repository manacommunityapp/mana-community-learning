package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.AcademyCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyCategoryRepository extends JpaRepository<AcademyCategoryEntity, String> {
    List<AcademyCategoryEntity> findByActiveTrueOrderByDisplayOrderAsc();
    Optional<AcademyCategoryEntity> findByCode(String code);
}
