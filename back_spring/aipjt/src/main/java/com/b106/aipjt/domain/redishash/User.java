package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;
import java.util.UUID;

@Getter @Setter
@RedisHash("user")
@ToString(of = {"id", "avatar", "nickname"})
public class User {
    @Id
    String id;
    int score = 0;
    String avatar;
    String nickname;

    public User(String id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.getId().equals(this.getId());
    }

}
