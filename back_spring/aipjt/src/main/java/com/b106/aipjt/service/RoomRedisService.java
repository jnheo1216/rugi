package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import com.b106.aipjt.domain.repository.RoundRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomRedisService {
    private final RoomRedisRepository roomRedisRepository;
    private final RoundRedisRepository roundRedisRepository;

//    public Optional<Room> createRoom(String avatar, String nickname) {
//        User user = new User(avatar, nickname);
//        Room room = new Room(null, user);
//        roomRedisRepository.save(room);
//        Optional<Room> id = Optional.ofNullable(room);
//        return id;
//    }

}
