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
    private S3UploadService s3UploadService;

    public void saveImage(QuestionDto questionDto) {
        questionRepository.save(questionDto.toEntity());
    }

    public List<QuestionDto> getList() {
        List<Question> galleryEntityList = questionRepository.findAll();
        List<QuestionDto> galleryDtoList = new ArrayList<>();

        for (Question question : galleryEntityList) {
            galleryDtoList.add(convertEntityToDto(question));
        }

        return galleryDtoList;
    }

    private QuestionDto convertEntityToDto(Question question) {
        return QuestionDto.builder()
            .imgName(question.getImgName())
            .imgFullPath(s3UploadService.CLOUD_FRONT_DOMAIN_NAME + "/" + question.getImgName())
            .build();
    }
}
