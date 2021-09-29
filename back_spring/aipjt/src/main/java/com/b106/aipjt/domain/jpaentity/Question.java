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
    private String imgUrl;
    private String imgCaption;

    public Question(String imgUrl, String imgCaption) {
        this.imgUrl = imgUrl;
        this.imgCaption = imgCaption;
    }


}
