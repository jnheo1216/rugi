package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

// 말 그대로 스킵을 담당하는 객체
@Getter @ToString
@RedisHash("skip")
@NoArgsConstructor
public class Skip {
    @Id
    private String id;
}
