package com.b106.aipjt.controller;


import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.domain.dto.room.RoomCreateRequestDto;
import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.service.RoomRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/room")
public class RoomController {

    private final RoomRedisService roomRedisService;

    // 메소드만 생성해둠
    @GetMapping("")
    public String getRoom(@RequestParam String roomId) throws Exception {
        System.out.println(roomId);
        return "a";
    }

    // 방 생성 : 현재 멤버를 미리 생성하는 메소드를 만들지, 멤버를 동시에 만들지 고민이 많은 상태
    // superUser의 id 정보를 노출하지 않는 것이 좋을 것 같은데, 어떻게 해야할까?? 엔티티 수정해야될지도
//    @PostMapping("")
//    public ResponseDto<RoomResponseDto> createRoom(@Valid @RequestBody RoomCreateRequestDto roomCreateDto) throws Exception{
//        Optional<Room> result = roomRedisService.createRoom(roomCreateDto.getAvatar(), roomCreateDto.getNickname());
//        if (result.isPresent()) {
//            Room room = result.get();
//            return new ResponseDto<>(HttpStatus.CREATED.value(), "방 생성 성공", new RoomResponseDto(room.getId(), room.getMemberList().get(0)));
//        }
//        throw new Exception("방 생성 실패");
//    }


}
