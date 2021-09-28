package com.b106.aipjt.controller;

import com.b106.aipjt.domain.jpaentity.Avatar;
import com.b106.aipjt.domain.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarRepository avatarRepository;

    @GetMapping("")
    public List<Avatar> findAllAvatar() {
        return avatarRepository.findAll();
    }

    @PostMapping("")
    public Avatar register(String avatarUrl) {
        System.out.println(avatarUrl);
        final Avatar avatar = Avatar.builder()
            .avatarUrl(avatarUrl)
            .build();
        return avatarRepository.save(avatar);
    }
}
