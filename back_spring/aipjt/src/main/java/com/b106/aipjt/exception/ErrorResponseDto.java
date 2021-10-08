package com.b106.aipjt.exception;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponseDto {
    private String errorCode;
    private String errorMessage;
    private Object data;

    public ErrorResponseDto(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
