package com.b106.aipjt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_NULL("001", "널"),
    RUN_TIME("002", "런타임에러");

    private String code;
    private String description;
}
