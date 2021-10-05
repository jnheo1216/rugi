package com.b106.aipjt.domain.dto.question;

import com.b106.aipjt.domain.jpaentity.Question;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {
    private String username;
}
