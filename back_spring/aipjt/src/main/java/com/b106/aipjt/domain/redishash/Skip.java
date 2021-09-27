package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

// 말 그대로 스킵을 담당하는 객체
@Getter
@RedisHash("skip")
public class Skip {
    @Id
    private String id;
}
