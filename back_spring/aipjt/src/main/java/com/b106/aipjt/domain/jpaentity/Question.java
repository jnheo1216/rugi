package com.b106.aipjt.domain.jpaentity;

import lombok.*;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity(name="question")
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imgName;
    private String imgFullPath;
    private String imgCaption;

    public Question(String imgUrl, String imgFullPath, String imgCaption) {
        this.imgName = imgUrl;
        this.imgFullPath = imgFullPath;
        this.imgCaption = imgCaption;
    }


}
