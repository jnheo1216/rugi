package com.b106.aipjt.domain.redishash;

import com.b106.aipjt.domain.jpaentity.Question;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private String caption; // 문제 캡션
    private String imgUrl; // 문제 이미지 주소
    private String audioUrl; // 캡션의 tts 주소
    @Builder.Default
    private List<UserImage> imageList = new ArrayList<>(); // 유저가 그린 이미지와 유저의 이름, 캡션 정보


    public static Quiz of(Question question) {
        return Quiz.builder()
            .caption(question.getImgCaption())
            .imgUrl(question.getImgUrl())
            .audioUrl(question.getAudioUrl())
            .build();
    }

    public String removeImgUrl() {
        String result = imgUrl;
        imgUrl = "";
        return result;
    }

    public void addImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
