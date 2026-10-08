package com.manacommunity.academy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "academy_quiz_questions", indexes = {
        @Index(name = "idx_question_quiz", columnList = "quiz_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    /** JSON array of answer choices: ["Option A", "Option B", "Option C", "Option D"] */
    @Column(name = "options_json", nullable = false, columnDefinition = "TEXT")
    private String optionsJson;

    @Column(name = "correct_option_index", nullable = false)
    private Integer correctOptionIndex;

    @Column(nullable = false)
    @Builder.Default
    private Integer points = 10;
}