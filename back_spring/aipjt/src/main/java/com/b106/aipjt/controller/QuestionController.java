package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.question.QuestionDto;
import com.b106.aipjt.service.QuestionService;
import com.b106.aipjt.service.S3UploadService;
import lombok.AllArgsConstructor;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/question")
@AllArgsConstructor
public class QuestionController {
    private S3UploadService s3UploadService;
    private QuestionService questionService;

    @GetMapping("")
    public String findAllQuestion(Model model){
        List<QuestionDto> questionDtoList = questionService.getList();

        model.addAttribute("questionList", questionDtoList);
        //      프론트 연결시 리턴값 추후 수정 필요
        return questionDtoList.toString();
    }

    @PostMapping("")
    public String register(QuestionDto questionDto, MultipartFile file) throws IOException {
        String imgUrl = s3UploadService.upload(file); // key : file
        questionDto.setImgName(imgUrl);
        questionService.saveImage(questionDto);

        return "upload success";
    }
}
