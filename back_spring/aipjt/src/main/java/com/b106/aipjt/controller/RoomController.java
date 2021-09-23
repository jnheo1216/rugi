package com.b106.aipjt.controller;


import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.domain.dto.room.RoomCreateRequestDto;
import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.dto.room.RoomUserResponseDto;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.service.RoomRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {
    private final RoomRedisService roomRedisService;

    // 방 생성 : 방 생성 전체정보를 굳이 보내 줄 필요가 있을까에 대한 의문이 생김. 어차피 방 입장할 때 조회하게 되므로
    @PostMapping("")
    public ResponseDto<RoomResponseDto> createRoom(@RequestHeader(value="User-Id") String userId,
                                                   @Valid @RequestBody RoomCreateRequestDto roomCreateDto){
        Room result = roomRedisService.createRoom(userId, roomCreateDto.getAvatar(), roomCreateDto.getNickname());
        RoomResponseDto build = RoomResponseDto.builder().id(result.getId()).build();
        System.out.println(build.getUserList());
        return new ResponseDto(HttpStatus.CREATED.value(), "방 생성 성공", build);
    }

    // 방 조회 : 유저의 방 입장에서의 ResponseDto 생성 로직이 겹치기 때문에, 함수로 따로 뺄 필요 있음.
    // 문제는 어디에 함수를 빼는가인데.. RoomResponseDto에 static 메소드 생성을 고려해 볼 것
    @GetMapping("")
    public ResponseDto<RoomResponseDto> getRoom(@RequestHeader(value="User-Id") String userId,
                                                @RequestParam String roomId){
        // 0. Room 조회
        Room room = roomRedisService.findOne(roomId);

        // 1.  User 리스트를 RoomUserResponseDto 변환ysql
        List<RoomUserResponseDto> roomUsers = new ArrayList<>();
        room.getUserList().forEach(u -> {
            roomUsers.add(new RoomUserResponseDto(0, u.getAvatar(), u.getNickname(), room.getSuperUser().getId().equals(u.getId())));
        });

        // 2. Room 객체를 RoomResponseDto 변환
        RoomResponseDto build = RoomResponseDto.builder()
            .id(room.getId())
            .round(room.getRound())
            .maxRound(room.getMaxRound())
            .roundTime(room.getRoundTime())
            .userList(roomUsers)
            .build();

        // 3. RoomResponseDto를 리턴
        return new ResponseDto(HttpStatus.OK.value(), "방 조회 성공", build);
    }




    // 유저의 방 입장 : 방 조회의 ResponseDto 생성 로직이 겹치기 때문에, 함수로 따로 뺄 필요 있음.
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
            .round(room.getRound())
            .maxRound(room.getMaxRound())
            .roundTime(room.getRoundTime())
            .userList(roomUsers)
            .build();

        // 3. RoomResponseDto를 리턴
        return new ResponseDto(HttpStatus.OK.value(), "방 입장 성공", build);
    }

    // 유저의 방 퇴장 : 내가 마지막 유저라면 ?? 방도 삭제해야 함
    @DeleteMapping("/user")
    public void leaveRoom(@RequestHeader(value="User-Id") String userId,
                                                 @RequestParam String roomId) {
        Room room = roomRedisService.leaveRoom(userId, roomId);
    }
}
