package com.b106.aipjt.domain.redishash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserImage {
    private String username; // 유저의 이름
    private String imgUrl; // 유저가 그린 이미지
    private String audioUrl; // 유저그림 캡셔닝을 읽은 것
    private String caption; // 유저가 그린 이미지의 캡셔닝
}
