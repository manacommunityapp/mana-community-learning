package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.entity.UserGamificationProfile;
import com.manacommunity.academy.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academy/gamification")
@RequiredArgsConstructor
public class GamificationController {

    private final GamificationService gamificationService;

    @GetMapping("/profile")
    public ResponseEntity<UserGamificationProfile> getProfile(
            @RequestParam Long communityId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(gamificationService.getOrCreateProfile(communityId, userId));
    }

    @PostMapping("/award")
    public ResponseEntity<UserGamificationProfile> awardXp(
            @RequestParam Long communityId,
            @RequestParam Long userId,
            @RequestParam int xp,
            @RequestParam(required = false) String badge) {
        return ResponseEntity.ok(gamificationService.awardXp(communityId, userId, xp, badge));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<Page<UserGamificationProfile>> getLeaderboard(
            @RequestParam Long communityId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(gamificationService.getLeaderboard(communityId, pageable));
    }
}