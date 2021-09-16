package com.b106.aipjt.controller;


import com.b106.aipjt.service.RoomRedisService;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {

    private final RoomRedisService roomRedisService;

    // 메소드만 생성해둠
    @GetMapping("")
    public String getRoom(@RequestParam String roomId) throws Exception{
        return "a";
    }

    // Body 검증 없음
    @PostMapping("")
    public RoomCreateResponseDto createRoom(@RequestBody RoomCreateRequestDto roomCreateDto) throws Exception{
        Optional<String> result = roomRedisService.createRoom(roomCreateDto.getAvatar(), roomCreateDto.getNickname());
        if (result.isPresent()) {
            return new RoomCreateResponseDto(result.get());
        }
        throw new Exception("방 생성 실패");
    }

    @Data
    public static class RoomCreateRequestDto {
        @NotNull
        private String avatar;
        @NotNull
        private String nickname;
    }

    @Data
    public static class RoomCreateResponseDto {
        private final String roomId;
    }
}
