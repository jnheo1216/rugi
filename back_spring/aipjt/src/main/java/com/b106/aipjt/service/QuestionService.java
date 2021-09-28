package com.b106.aipjt.service;

import com.b106.aipjt.domain.dto.question.QuestionDto;
import com.b106.aipjt.domain.jpaentity.Question;
import com.b106.aipjt.domain.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {
    private QuestionRepository questionRepository;
    private S3UploadService s3UploadService;
    // DB에 저장
    public void saveImage(QuestionDto questionDto) {
        questionRepository.save(questionDto.toEntity());
    }

    public List<QuestionDto> getList() {
        List<Question> questionEntityList = questionRepository.findAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        // dto 객체 사용을 위해 entity 객체를 dto로 변환?
        for (Question question : questionEntityList) {
            questionDtoList.add(convertEntityToDto(question));
        }
        return questionDtoList;
    }

    private QuestionDto convertEntityToDto(Question question) {
        return QuestionDto.builder()
            .imgName(question.getImgName())
            .imgFullPath(s3UploadService.CLOUD_FRONT_DOMAIN_NAME + "/" + question.getImgName())
            .build();
    }

}
