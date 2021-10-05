package com.b106.aipjt.domain.dto.caption;

import lombok.Data;

@Data // 응답 DTO
public class CaptResponse {
    private String caption;
    private String audio;
}
