package com.manacommunity.academy.controller;

import com.manacommunity.academy.dto.CategoryResponseDto;
import com.manacommunity.academy.repository.AcademyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/academy/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademyCategoryController {

    private final AcademyCategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getActiveCategories() {
        return ResponseEntity.ok(categoryRepository.findByActiveTrueOrderByDisplayOrderAsc().stream()
                .map(CategoryResponseDto::fromEntity)
                .collect(Collectors.toList()));
    }
}
