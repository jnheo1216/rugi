package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRedisService {

    private final UserRedisRepository userRedisRepository;

   // 유저가 이미 있는 경우 아바타와 닉네임만 변경해주는로직
    public User join(String userId, String avatar, String nickname) {
    if (userId != null) { // 유저가 있을 경우
        // 유저가 있을 경우 조회해서 avatar랑 nickname을 바꿔준다
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isPresent()) { // 유저가 있을 경우 avatar와 nickname을 바꿔준다
            return userRedisRepository.save(new User(userId, avatar, nickname));
        }
    }
        return userRedisRepository.save(new User(null, avatar, nickname));
    }

   // 유저 조회
    public User findOne(String userId) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isEmpty()) {
            throw new RuntimeException("일치하는 유저가 없습니다");
        }
        return result.get();
    }
}
