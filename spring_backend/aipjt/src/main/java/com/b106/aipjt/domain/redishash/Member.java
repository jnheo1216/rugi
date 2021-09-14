package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Member {
    String id;
    int score = 0;
    boolean isActive = true;
    String avatar;
    String nickname;

    public Member(String avatar, String nickname) {
        this.id = UUID.randomUUID().toString();
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
