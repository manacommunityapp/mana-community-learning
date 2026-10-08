package com.manacommunity.academy.controller;

import com.manacommunity.academy.domain.entity.CourseLessonEntity;
import com.manacommunity.academy.domain.entity.CourseModuleEntity;
import com.manacommunity.academy.domain.entity.LessonProgressEntity;
import com.manacommunity.academy.service.CourseCurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/academy/curriculum")
@RequiredArgsConstructor
public class CourseCurriculumController {

    private final CourseCurriculumService curriculumService;

    @PostMapping("/modules")
    public ResponseEntity<CourseModuleEntity> createModule(@RequestBody CourseModuleEntity module) {
        return ResponseEntity.ok(curriculumService.createModule(module));
    }

    @GetMapping("/programs/{programId}/modules")
    public ResponseEntity<List<CourseModuleEntity>> getModules(@PathVariable Long programId) {
        return ResponseEntity.ok(curriculumService.getModulesForProgram(programId));
    }

    @PostMapping("/lessons")
    public ResponseEntity<CourseLessonEntity> createLesson(@RequestBody CourseLessonEntity lesson) {
        return ResponseEntity.ok(curriculumService.createLesson(lesson));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<CourseLessonEntity>> getLessons(@PathVariable Long moduleId) {
        return ResponseEntity.ok(curriculumService.getLessonsForModule(moduleId));
    }

    @PostMapping("/lessons/{lessonId}/progress")
    public ResponseEntity<LessonProgressEntity> updateProgress(
            @PathVariable Long lessonId,
            @RequestParam Long communityId,
            @RequestParam Long studentUserId,
            @RequestBody Map<String, Object> body) {
        boolean completed = body.containsKey("completed") && Boolean.parseBoolean(body.get("completed").toString());
        int positionSeconds = body.containsKey("positionSeconds") ? Integer.parseInt(body.get("positionSeconds").toString()) : 0;
        return ResponseEntity.ok(curriculumService.recordLessonProgress(communityId, studentUserId, lessonId, completed, positionSeconds));
    }
}