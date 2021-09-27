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
    private int timeout;
    private String question; // 질문
    private Long answerURL; // 정답에 대한 URL을 가지고 있는 엔티티의 id를 저장한다
}
