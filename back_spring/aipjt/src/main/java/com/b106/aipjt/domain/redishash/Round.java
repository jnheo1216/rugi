package com.b106.aipjt.domain.redishash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@NoArgsConstructor @AllArgsConstructor
@Getter @RedisHash("round")
public class Round {
    @Id
    private String id;
    @TimeToLive
    private Long timeout;
    private String question; // 질문

}
