package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.Round;
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
    private final RoundRedisRepository roundRedisRepository;
    private final RoomRedisRepository roomRedisRepository;

    // 방 생성
    public Room createRoom(String userId) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다.");
        Room room = roomRedisRepository.save(new Room(null, result.get()));
        return roomRedisRepository.save(room);
    }


    // 방 입장
    public Room joinRoom(String userId, String roomId) {
        // 객체 조회
        Optional<User> userById = userRedisRepository.findById(userId);
        Optional<Room> roomById = roomRedisRepository.findById(roomId);
        // 검증 로직
        if (userById.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다");
        if (roomById.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        //Optional 꺼내기
        User user = userById.get();
        Room room = roomById.get();
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
