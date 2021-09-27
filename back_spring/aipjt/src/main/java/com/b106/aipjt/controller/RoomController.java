package com.b106.aipjt.controller;


import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.dto.room.RoomUserResponseDto;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.service.RoomRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {
    private final RoomRedisService roomRedisService;

    // 방 생성
    @PostMapping("")
    public ResponseDto<RoomResponseDto> createRoom(@RequestHeader(value="User-Id") String userId){
        Room result = roomRedisService.createRoom(userId);
        RoomResponseDto build = RoomResponseDto.builder().id(result.getId()).build();
        System.out.println(build.getUserList());
        return new ResponseDto(HttpStatus.CREATED.value(), "방 생성 성공", build);
    }

    // 유저의 방 입장
    @PostMapping("/user")
    public ResponseDto<RoomResponseDto> joinRoom(@RequestHeader(value="User-Id") String userId,
                                                 @RequestParam String roomId) {
        Room room = roomRedisService.joinRoom(userId, roomId);

        // 1.  User 리스트를 RoomUserResponseDto 변환
        List<RoomUserResponseDto> roomUsers = new ArrayList<>();
        room.getUserList().forEach(u -> {
            roomUsers.add(new RoomUserResponseDto(0, u.getAvatar(), u.getNickname(), room.getSuperUser().getId().equals(u.getId())));
        });

        // 2. Room 객체를 RoomResponseDto 변환
        RoomResponseDto build = RoomResponseDto.builder()
            .id(room.getId())
            .currentRound(room.getCurrentRound())
            .maxRound(room.getMaxRound())
            .roundTime(room.getRoundTime())
            .userList(roomUsers)
            .build();

        // 3. RoomResponseDto를 리턴
        return new ResponseDto(HttpStatus.OK.value(), "방 입장 성공", build);
    }

    // 유저의 방 퇴장
    @DeleteMapping("/user")
    public void leaveRoom(@RequestHeader(value="User-Id") String userId,
                                                 @RequestParam String roomId) {
        Room room = roomRedisService.leaveRoom(userId, roomId);
    }
}
