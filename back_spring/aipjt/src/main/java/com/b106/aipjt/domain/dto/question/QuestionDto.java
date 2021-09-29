package com.b106.aipjt.domain.dto.question;

import com.b106.aipjt.domain.jpaentity.Question;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class QuestionDto {
    private String imgUrl;
    private String imgCaption;

    public Question toEntity(){
        Question build = Question.builder()
            .imgUrl(imgUrl)
            .imgCaption(imgCaption)
            .build();

        return build;
    }

    public QuestionDto(String imgUrl, String imgCaption) {
        this.imgUrl = imgUrl;
        this.imgCaption = imgCaption;
    }
}
