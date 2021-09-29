package com.b106.aipjt.service;

import com.b106.aipjt.domain.dto.caption.CaptRequest;
import com.b106.aipjt.domain.dto.caption.CaptResponse;
import com.b106.aipjt.domain.dto.question.QuestionDto;
import com.b106.aipjt.domain.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CaptService {
    private QuestionDto questionDto;

    public String postCaption(QuestionDto questionDto) {
        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:8000")
            .path("/api/image_caption/")
            .build()
            .toUri();

        System.out.println("@ uri 확인: " + uri.toString());

        CaptRequest req = new CaptRequest();
        System.out.println("@ questionDto: "+questionDto);
        req.setImage_path(questionDto.getImgUrl());
        // 상세정보를 알기 위해서 ResponseEntity 받기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<CaptResponse> res = rt.postForEntity(uri, req, CaptResponse.class);
        System.out.println("@ BODY 확인: "+res.getBody());
        System.out.println("@ CAPTION 확인: "+res.getBody().getCaption());
        return res.getBody().getCaption();
    }
}
