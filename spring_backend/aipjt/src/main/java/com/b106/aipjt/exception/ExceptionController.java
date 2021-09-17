package com.b106.aipjt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NullPointerException.class, RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        e.printStackTrace();
        if (e.getClass()==RuntimeException.class){
            return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(ErrorCode.RUN_TIME.getCode(), ErrorCode.RUN_TIME.getDescription(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(ErrorCode.NOT_NULL.getCode(), ErrorCode.NOT_NULL.getDescription(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
