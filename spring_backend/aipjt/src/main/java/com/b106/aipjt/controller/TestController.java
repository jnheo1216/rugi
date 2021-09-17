package com.b106.aipjt.controller;

import com.b106.aipjt.service.RoomRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RoomRedisService roomRedisService;

    @GetMapping("/test")
    public String hello() {
        return "Hello, test success";
    }

    @GetMapping("/redis")
    public String redis() {
        Optional<String> roomId = roomRedisService.createRoom("/avatar/test", "준영");
        return roomId.get();
    }
}
