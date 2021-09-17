package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.Member;
import com.b106.aipjt.domain.redishash.Room;
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

    public Optional<String> createRoom(String avatar, String nickname) {
        Member member = new Member(avatar, nickname);
        Room room = new Room(null, member);
        roomRedisRepository.save(room);
        Optional<String> id = Optional.ofNullable(room.getId());
        return id;
    }

}
