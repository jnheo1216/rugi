package com.b106.aipjt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InvalidDataDto {
    String field;
    Object value;
    String message;
    String code;
}
