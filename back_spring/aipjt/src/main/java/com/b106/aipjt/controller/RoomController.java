package com.b106.aipjt.controller;


import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.service.RoomRedisService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
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

    // 방 생성
    @PostMapping("")
    public RoomResponseDto createRoom(@Valid @RequestBody RoomCreateRequestDto roomCreateDto) throws Exception{
        Optional<Room> result = roomRedisService.createRoom(roomCreateDto.getAvatar(), roomCreateDto.getNickname());
        if (result.isPresent()) {
//            객체를 만들어주어야 한다
            Room room = result.get();
            return new RoomResponseDto(room.getId(), room.getMemberList().get(0));
        }
        throw new Exception("방 생성 실패");
    }

    @Data
    public static class RoomCreateRequestDto {
        @NotEmpty(message = "아바타를 골라야 합니다")
        private String avatar;
        @NotEmpty(message = "닉네임을 적어주세요")
        private String nickname;
    }

    @Data
    public static class RoomResponseDto {
        private String id;
        private String superUser;
        private boolean isStart = false;
        private int round = 0;
        private int maxRound = 3;
        private int roundTime = 60;
        private List<UserResponseDto> memberList = new ArrayList();

        public RoomResponseDto(String id, User user) {
            this.id = id;
            this.superUser = user.getId();
            this.memberList.add(new UserResponseDto(user.getId(), user.getAvatar(), user.getNickname()));
        }
    }

    @Data
    public static class UserResponseDto {
        String id;
        int score = 0;
        boolean isActive = true;
        String avatar;
        String nickname;


        public UserResponseDto(String id, String avatar, String nickname) {
            this.id = id;
            this.avatar = avatar;
            this.nickname = nickname;
        }
    }

}
