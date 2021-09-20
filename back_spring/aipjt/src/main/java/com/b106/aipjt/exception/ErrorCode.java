package com.b106.aipjt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_NULL("001", "nullPointerException"),
    RUN_TIME("002", "RuntimeException"),
    INVALID_DATA("003", "유효하지 않은 데이터"),
    NO_USER_HEADER("004", "필요 헤더가 존재하지 않습니다.");

    private String code;
    private String description;
}
