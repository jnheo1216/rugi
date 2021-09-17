package com.b106.aipjt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String code;
    private String description;
    private String detail;


    public ErrorResponseDto(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
