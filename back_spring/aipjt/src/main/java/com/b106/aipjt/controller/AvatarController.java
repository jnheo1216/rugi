package com.b106.aipjt.controller;

import com.b106.aipjt.domain.jpaentity.Avatar;
import com.b106.aipjt.domain.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarRepository avatarRepository;

    @GetMapping("")
    public List<Avatar> findAllAvatar() {
        return avatarRepository.findAll();
    }

    @PostMapping("")
    public Avatar register() {
        final Avatar avatar = Avatar.builder()
            .avatarUrl("test_avatar")
            .build();
        return avatarRepository.save(avatar);
    }
}
