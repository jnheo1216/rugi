package com.b106.aipjt.service;

import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.dto.socket.RoomInfoMessageDto;
import com.b106.aipjt.domain.redishash.GameStatus;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomRedisService {
    private final UserRedisRepository userRedisRepository;
    private final RoomRedisRepository roomRedisRepository;
    private final SimpMessagingTemplate template; // socket 메시지 주고받기용
    // 방 생성
    public Room createRoom(String userId) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다.");
        Room room = roomRedisRepository.save(new Room(null, result.get()));
        return roomRedisRepository.save(room);
    }
    // 방 조회
    public Room getRoom(String userId, String roomId) {
        Optional<User> userById = userRedisRepository.findById(userId);
        Optional<Room> roomById = roomRedisRepository.findById(roomId);
        // 검증 로직
        if (userById.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다");
        if (roomById.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        User user = userById.get();
        Room room = roomById.get();
        if (room.isFull()) throw new RuntimeException("방이 가득 찼습니다.");
        return room;
    }

    // 방 수정
    public Room configRoom(String userId, String roomId, int maxRound, int roundTime, int personnel) {
        Optional<Room> roomById = roomRedisRepository.findById(roomId);
        if (roomById.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다.");
        Room room = roomById.get();
        if (!room.getSuperUser().getId().equals(userId)) throw new RuntimeException("방장만 방을 변경할 수 있습니다.");
        if (!room.getStatus().equals(GameStatus.LOBBY.getValue())) throw new RuntimeException("게임중에는 방 설정을 변경할 수 없습니다.");
        room = room.gameSetting(maxRound, roundTime, personnel);
        return roomRedisRepository.save(room);
    }

    // 방 입장
    public Room joinRoom(String userId, String avatar, String nickname, String roomId) {
        // 객체 조회
        boolean userExist = userRedisRepository.existsById(userId);
        Optional<Room> roomById = roomRedisRepository.findById(roomId);
        // 검증 로직
        if (!userExist) throw new RuntimeException("유저가 존재하지 않습니다");
        if (roomById.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        //Optional 꺼내기
        Room room = roomById.get();
        if (room.isFull()) throw new RuntimeException("방이 가득 찼습니다");

        // 유저 정보 갱신 & 저장
        User user = userRedisRepository.save(new User(userId, avatar, nickname));
        // 입장 처리하기
        if (!room.getUserList().contains(user)) {
            room.getUserList().add(user);
            roomRedisRepository.save(room);
        }
        return room;
    }

    // 방 퇴장 : 방장 변경 로직 추가
    public Room leaveRoom(String userId, String roomId) {
        // 조회 & 검증 & Optional 빼내기
        Optional<Room> roomResult = roomRedisRepository.findById(roomId);
        if (roomResult.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        Room room = roomResult.get();

        // 퇴장을 위한 필터링
        List<User> collect = room.getUserList()
            .stream()
            .filter(u -> !u.getId().equals(userId))
            .collect(Collectors.toList());
        room.setUserList(collect);

        if (collect.size() == 0) {
            roomRedisRepository.delete(room);
        }else{
            // 본인이 방장인지 확인한다
            if (room.getSuperUser().getId().equals(userId)) {
                room.setSuperUser(room.getUserList().get(0));
            }
            roomRedisRepository.save(room);
        }
        return room;
    }
    public void makeRoomInfoMessage(RoomResponseDto room) {
        RoomInfoMessageDto messageDto = new RoomInfoMessageDto();
        messageDto.setRoomId(room.getId());
        messageDto.setMessage(room);
        template.convertAndSend("/sub/info/room/" + messageDto.getRoomId(), messageDto);
    }


}
