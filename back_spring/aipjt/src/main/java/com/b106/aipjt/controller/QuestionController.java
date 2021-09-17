package com.b106.aipjt.controller;

import com.b106.aipjt.domain.jpaentity.Question;
import com.b106.aipjt.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("question")
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }

    @PostMapping("question")
    public Question register() {
        final Question question = Question.builder()
            .img_url("test_url")
            .img_caption("test_caption")
            .build();
        return questionRepository.save(question);
    }
}
