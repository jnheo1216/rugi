package com.b106.aipjt.service;

import com.b106.aipjt.domain.dto.question.QuestionDto;
import com.b106.aipjt.domain.jpaentity.Question;
import com.b106.aipjt.domain.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            .build();
    }

}
