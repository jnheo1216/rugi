package com.b106.aipjt.controller;


import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.domain.dto.room.RoomJoinRequestDto;
import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.dto.room.RoomSettingRequestDto;
import com.b106.aipjt.domain.dto.room.RoomUserResponseDto;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.service.RoomRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {
    private final RoomRedisService roomRedisService;
    private final SimpMessagingTemplate template; // socket 메시지 주고받기용

    // 방 생성
    @PostMapping("")
    public ResponseDto<RoomResponseDto> createRoom(@RequestHeader(value="User-Id") String userId){
        Room result = roomRedisService.createRoom(userId);
        RoomResponseDto build = RoomResponseDto.builder().id(result.getId()).build();
        System.out.println(build.getUserList());
        return new ResponseDto<>(HttpStatus.CREATED.value(), "방 생성 성공", build);
    }

    // 방 조회
    @GetMapping("")
    public ResponseDto<RoomResponseDto> getRoom(@RequestHeader(value="User-Id") String userId,
                                                  @RequestParam String roomId){
        Room room = roomRedisService.getRoom(userId, roomId);
        List<RoomUserResponseDto> roomUsers = RoomUserResponseDto.of(room);
        RoomResponseDto build = RoomResponseDto.toRoom(room, roomUsers);
        System.out.println(build.getUserList());

        return new ResponseDto<>(HttpStatus.CREATED.value(), "방 조회 성공", build);
    }

    // 방 수정 : 방정보에 대한 변경 사항을 받아야 한다
    @PatchMapping("")
    public ResponseDto<RoomResponseDto> patchRoom(@RequestHeader(value="User-Id") String userId,
                                                  @RequestParam String roomId,
                                                  @RequestBody RoomSettingRequestDto roomSettingRequestDto){
        Room room = roomRedisService.configRoom(userId, roomId, roomSettingRequestDto.getMaxRound(), roomSettingRequestDto.getRoundTime(), roomSettingRequestDto.getPersonnel());

        List<RoomUserResponseDto> roomUsers = RoomUserResponseDto.of(room);

        RoomResponseDto build = RoomResponseDto.toRoom(room, roomUsers);
        roomRedisService.makeRoomInfoMessage(build);
        System.out.println(build.getUserList());

        return new ResponseDto<>(HttpStatus.CREATED.value(), "방 수정 성공", build);
    }

    // 유저의 방 입장 : 여기서 id만 보내는게 아니라 아바타랑 닉네임도 보내야 함
    // RoomEnterRequestDto를 수정할 것. 실제로 백에 보내는 요청은 굳이 Param으로 보낼 필요 없으니 Body로 빼도 될듯
    @PostMapping("/user")
    public ResponseDto<RoomResponseDto> joinRoom(@RequestHeader(value="User-Id") String userId,
                                                 @RequestBody RoomJoinRequestDto roomJoinRequestDto,
                                                 @RequestParam String roomId) {
        Room room = roomRedisService.joinRoom(userId, roomJoinRequestDto.getAvatar(), roomJoinRequestDto.getNickname(), roomId);

        // 1.  User 리스트를 RoomUserResponseDto 변환
        List<RoomUserResponseDto> roomUsers = RoomUserResponseDto.of(room);

        // 2. Room 객체를 RoomResponseDto 변환
        RoomResponseDto build = RoomResponseDto.toRoom(room, roomUsers);

        // 방에 있는 다른 사람들에게 다시 방객체 정보 publish
        roomRedisService.makeRoomInfoMessage(build);

        // 3. RoomResponseDto를 리턴
        return new ResponseDto<>(HttpStatus.OK.value(), "방 입장 성공", build);
    }

    // 유저의 방 퇴장
    @DeleteMapping("/user")
    public ResponseDto<RoomResponseDto> leaveRoom(@RequestHeader(value="User-Id") String userId,
                                                 @RequestParam String roomId) {
        Room room = roomRedisService.leaveRoom(userId, roomId);
        // 유저 퇴장했음을 채팅 창에 알림

        // UserList 새로 작성
        List<RoomUserResponseDto> roomUsers = RoomUserResponseDto.of(room);
        
        RoomResponseDto build = RoomResponseDto.toRoom(room, roomUsers);

        // 방에 있는 다른 사람들에게 다시 방객체 정보 publish
        roomRedisService.makeRoomInfoMessage(build);
        return new ResponseDto<>(HttpStatus.OK.value(), "멤버 퇴장", build);
    }


}
