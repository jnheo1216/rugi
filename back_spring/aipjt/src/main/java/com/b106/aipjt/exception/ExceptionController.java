package com.b106.aipjt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {



    @ExceptionHandler({NullPointerException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        e.printStackTrace();
        if (e.getClass()==RuntimeException.class){
            return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(ErrorCode.RUN_TIME.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(ErrorCode.NOT_NULL.getCode(), ErrorCode.NOT_NULL.getDescription(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> argumentNotValid(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<InvalidDataDto> invalidDatas = new ArrayList<>();
        fieldErrors.forEach(f -> {invalidDatas.add(new InvalidDataDto(f.getField(), f.getRejectedValue(), f.getDefaultMessage(), f.getCode()));});
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.INVALID_DATA.getCode(), ErrorCode.INVALID_DATA.getDescription(), invalidDatas);
        return new ResponseEntity(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    // 헤더 정보가 없을 때
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDto> missingUserHeader(MissingRequestHeaderException e) {
        e.printStackTrace();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.NO_USER_HEADER.getCode(), ErrorCode.NO_USER_HEADER.getDescription());
        return new ResponseEntity(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
