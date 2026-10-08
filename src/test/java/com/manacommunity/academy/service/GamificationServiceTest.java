package com.manacommunity.academy.service;

import com.manacommunity.academy.domain.entity.UserGamificationProfile;
import com.manacommunity.academy.repository.UserGamificationProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GamificationService unit tests")
class GamificationServiceTest {

    @Mock private UserGamificationProfileRepository profileRepository;
    @InjectMocks private GamificationService gamificationService;

    @Test
    @DisplayName("awardXp: correctly accumulates XP, calculates level and adds badge")
    void awardXp_calculatesLevelAndBadge() {
        UserGamificationProfile profile = UserGamificationProfile.builder()
                .id(1L)
                .communityId(10L)
                .userId(100L)
                .xpPoints(80)
                .level(1)
                .streakDays(1)
                .badgesJson("[]")
                .build();

        when(profileRepository.findByCommunityIdAndUserId(10L, 100L)).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(UserGamificationProfile.class))).thenAnswer(inv -> inv.getArgument(0));

        UserGamificationProfile updated = gamificationService.awardXp(10L, 100L, 50, "FIRST_QUIZ");

        assertThat(updated.getXpPoints()).isEqualTo(130);
        assertThat(updated.getLevel()).isEqualTo(2); // 1 + (130 / 100)
        assertThat(updated.getBadgesJson()).contains("FIRST_QUIZ");
        verify(profileRepository).save(profile);
    }
}