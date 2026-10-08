package com.manacommunity.academy.repository;

import com.manacommunity.academy.domain.entity.UserGamificationProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGamificationProfileRepository extends JpaRepository<UserGamificationProfile, Long> {
    Optional<UserGamificationProfile> findByCommunityIdAndUserId(Long communityId, Long userId);
    Optional<UserGamificationProfile> findByUserId(Long userId);
    Page<UserGamificationProfile> findByCommunityIdOrderByXpPointsDesc(Long communityId, Pageable pageable);
}