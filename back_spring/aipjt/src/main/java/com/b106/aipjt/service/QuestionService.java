package com.b106.aipjt.service;

import com.b106.aipjt.domain.dto.caption.CaptRequest;
import com.b106.aipjt.domain.dto.caption.CaptResponse;
import com.b106.aipjt.domain.dto.question.QuestionDto;
import com.b106.aipjt.domain.jpaentity.Question;
import com.b106.aipjt.domain.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private QuestionRepository questionRepository;

    // DB에 저장
    public void saveImage(QuestionDto questionDto) {
        questionRepository.save(questionDto.toEntity());
    }

    public List<QuestionDto> getList() {
        List<Question> questionEntityList = questionRepository.findAll();
        System.out.println("@QuestionDto getList: "+questionEntityList);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        // dto 객체 사용을 위해 entity 객체를 dto로 변환?
        for (Question question : questionEntityList) {
            questionDtoList.add(convertEntityToDto(question));
        }
        return questionDtoList;
    }

    private QuestionDto convertEntityToDto(Question question) {
        return QuestionDto.builder()
            .imgUrl(question.getImgUrl())
            .imgCaption(question.getImgCaption())
            .audioUrl(question.getAudioUrl())
            .build();
    }

    // imgpath주고, caption 받기
    public ResponseEntity imgUrlPost(QuestionDto questionDto) {
        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:8000")
            .path("/api/image_caption/kr/")
            .build()
            .toUri();

        CaptRequest req = new CaptRequest();
        req.setImage_path(questionDto.getImgUrl());
        // 상세정보를 알기 위해서 ResponseEntity 받기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<CaptResponse> res = rt.postForEntity(uri, req, CaptResponse.class);
        System.out.println("@ BODY 확인: "+res.getBody());
        return res;
    }
}
