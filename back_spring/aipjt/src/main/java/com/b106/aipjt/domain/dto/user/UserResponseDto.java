package com.b106.aipjt.domain.dto.user;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserResponseDto {
    String id;
    String avatar;
    String nickname;

    public UserResponseDto(String id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
