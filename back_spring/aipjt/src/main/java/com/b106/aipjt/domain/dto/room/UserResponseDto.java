package com.b106.aipjt.domain.dto.room;

import lombok.Data;

@Data
public class UserResponseDto {
    String id;
    int score = 0;
    boolean isActive = true;
    String avatar;
    String nickname;


    public UserResponseDto(String id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
