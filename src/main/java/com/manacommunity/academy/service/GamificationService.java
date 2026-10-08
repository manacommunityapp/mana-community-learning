package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.UserGamificationProfile;
import com.manacommunity.academy.repository.UserGamificationProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserGamificationProfileRepository profileRepository;

    @Transactional
    public UserGamificationProfile getOrCreateProfile(Long communityId, Long userId) {
        return profileRepository.findByCommunityIdAndUserId(communityId, userId)
                .orElseGet(() -> profileRepository.save(UserGamificationProfile.builder()
                        .communityId(communityId)
                        .userId(userId)
                        .xpPoints(0)
                        .level(1)
                        .streakDays(1)
                        .lastActiveDate(LocalDate.now())
                        .build()));
    }

    @Transactional
    public UserGamificationProfile awardXp(Long communityId, Long userId, int xpAmount, String badgeName) {
        UserGamificationProfile profile = getOrCreateProfile(communityId, userId);
        profile.addXp(xpAmount);

        // Update streak
        LocalDate today = LocalDate.now();
        if (profile.getLastActiveDate() == null || profile.getLastActiveDate().isBefore(today.minusDays(1))) {
            profile.setStreakDays(1);
        } else if (profile.getLastActiveDate().equals(today.minusDays(1))) {
            profile.setStreakDays(profile.getStreakDays() + 1);
        }
        profile.setLastActiveDate(today);

        // Add badge if provided and not already earned
        if (badgeName != null && !badgeName.isBlank()) {
            String currentBadges = profile.getBadgesJson();
            if (!currentBadges.contains(badgeName)) {
                if (currentBadges.equals("[]")) {
                    profile.setBadgesJson("[\"" + badgeName + "\"]");
                } else {
                    profile.setBadgesJson(currentBadges.replace("]", ",\"" + badgeName + "\"]"));
                }
            }
        }

        log.info("Gamification: User id={} awarded {} XP (Total: {}, Level: {})",
                userId, xpAmount, profile.getXpPoints(), profile.getLevel());
        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Page<UserGamificationProfile> getLeaderboard(Long communityId, Pageable pageable) {
        return profileRepository.findByCommunityIdOrderByXpPointsDesc(communityId, pageable);
    }
}