package com.b106.aipjt.domain.dto.question;

import com.b106.aipjt.domain.jpaentity.Question;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class QuestionDto {
    private String imgName;
    private String imgCaption;
    private String imgFullPath;

    public Question toEntity(){
        Question build = Question.builder()
            .imgName(imgName)
            .imgFullPath(imgFullPath)
            .imgCaption(imgCaption)
            .build();

        return build;
    }

    public QuestionDto(String imgName, String imgCaption, String imgFullPath) {
        this.imgName = imgName;
        this.imgCaption = imgCaption;
        this.imgFullPath = imgFullPath;
    }
}
