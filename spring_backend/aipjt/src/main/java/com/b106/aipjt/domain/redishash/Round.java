package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("round")
public class Round {
    @Id
    private String id;
    @TimeToLive
    private Long timeout;
    private String question; // 질문내용

    public Round(String id, Long timeout, String question) {
        this.id = id;
        this.timeout = timeout;
        this.question = question;
    }
}
