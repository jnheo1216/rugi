package com.b106.aipjt.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class ResponseDto<T> {
    private int statusCode; // 상태코드
    private String message; // 메시지
    private T data; // 응답데이터
}
