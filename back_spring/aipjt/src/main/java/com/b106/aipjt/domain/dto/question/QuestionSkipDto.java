package com.b106.aipjt.domain.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSkipDto {
    private QuestionDto question;
    private String skip;
}
