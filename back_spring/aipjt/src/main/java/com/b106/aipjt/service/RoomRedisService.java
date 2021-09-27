package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import com.b106.aipjt.domain.repository.RoundRedisRepository;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomRedisService {
    private final UserRedisRepository userRedisRepository;
    private final RoomRedisRepository roomRedisRepository;

    // 방 생성
    public Room createRoom(String userId, String avatar, String nickname) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isEmpty()) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
        User user = result.get();
        user.setAvatar(avatar);
        user.setNickname(nickname);
        userRedisRepository.save(user);
        Room room = new Room(null, user);
        return roomRedisRepository.save(room);
    }

    // 방 조회
    public Room findOne(String roomId) {
        Optional<Room> result = roomRedisRepository.findById(roomId);
        if (result.isEmpty()) {
            throw new RuntimeException("방이 존재하지 않습니다.");
        }
        return result.get();
    }

    // 방 입장 : 중복 입장이 안되도록 처리가 필요함 -> 나중에 다시 확인할 것. filter를 사용하는게 맞을지..?
    public Room joinRoom(String userId, String roomId) {
        // 객체 조회
        Optional<User> userResult = userRedisRepository.findById(userId);
        Optional<Room> roomResult = roomRedisRepository.findById(roomId);
        // 검증 로직
        if (userResult.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다");
        if (roomResult.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        //Optional 꺼내기
        User user = userResult.get();
        Room room = roomResult.get();
        // 입장 처리하기
        if (!room.getUserList().contains(user)) {
            room.getUserList().add(user);
            roomRedisRepository.save(room);
        }

        return room;
    }

    // 방 퇴장
    public Room leaveRoom(String userId, String roomId) {
        // 조회 & 검증 & Optional 빼내기
        Optional<Room> roomResult = roomRedisRepository.findById(roomId);
        if (roomResult.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        Room room = roomResult.get();
        // 퇴장을 위한 필터링
        List<User> collect = room.getUserList()
            .stream()
            .filter(u -> u.getId() == userId)
            .collect(Collectors.toList());
        room.setUserList(collect);
        if (collect.size() == 0) {
            roomRedisRepository.delete(room);
        }else{
            roomRedisRepository.save(room);
        }
        return room;
    }
}
