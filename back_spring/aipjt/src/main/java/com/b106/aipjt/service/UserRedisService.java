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

   // 유저 생성
    public User join(String userId, String avatar, String nickname) {
        return userRedisRepository.save(new User(userId, avatar, nickname));
    }

   // 유저 조회
    public User findOne(String userId) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isEmpty()) {
            throw new RuntimeException("일치하는 유저가 없습니다");
        }
        return result.get();
    }
    //유저 삭제
    public void remove(String userId) {
        Optional<User> result = userRedisRepository.findById(userId);
        if (result.isPresent()) {
            userRedisRepository.delete(result.get());
        }
    }
}
