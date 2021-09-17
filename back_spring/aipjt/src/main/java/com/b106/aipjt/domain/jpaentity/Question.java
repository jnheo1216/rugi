package com.b106.aipjt.domain.jpaentity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity(name="question")
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String img_url;
    private String img_caption;

    public Question(String img_url, String img_caption) {
        this.img_url = img_url;
        this.img_caption = img_caption;
    }


}
