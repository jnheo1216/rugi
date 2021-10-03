package com.b106.aipjt.domain.redishash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {
    LOBBY("LOBBY", "게임 로비 상태"),
    INIT("INIT", "게임 시작 전 문제 제시"),
    PLAY("PLAY", "게임 중"),
    RESULT("RESULT", "라운드 결과"),
    END("END", "최종 결과");

    private String value;
    private String description;
}
