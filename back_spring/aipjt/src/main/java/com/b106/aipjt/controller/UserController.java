package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.domain.dto.user.UserCreateDto;
import com.b106.aipjt.domain.dto.user.UserResponseDto;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.service.UserRedisService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserRedisService userRedisService;

    @GetMapping("")
    public ResponseDto<UserResponseDto> getUser(@RequestHeader(value="User-Id") String userId) {
        User user = userRedisService.findOne(userId);
        System.out.println(userId);
        return new ResponseDto(
            HttpStatus.OK.value(),
            "유저 조회",
            new UserResponseDto(user.getId(), user.getAvatar(), user.getNickname()));
    }


    @PostMapping("")
    public ResponseDto<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto data) {
        User user = userRedisService.join(data.getId(), data.getAvatar(), data.getNickname());
        return new ResponseDto(
            HttpStatus.OK.value(),
            "유저 생성",
            new UserResponseDto(user.getId(), user.getAvatar(), user.getNickname()));
    }
}
