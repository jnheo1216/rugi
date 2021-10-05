package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.caption.CaptResponse;
import com.b106.aipjt.domain.dto.question.QuestionRequestDto;
import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import com.b106.aipjt.domain.jpaentity.Question;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.UserImage;
import com.b106.aipjt.domain.repository.QuestionRepository;
import com.b106.aipjt.service.GameService;
import com.b106.aipjt.service.QuestionService;
import com.b106.aipjt.service.RoomRedisService;
import com.b106.aipjt.service.S3UploadService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

    private S3UploadService s3UploadService;
    private QuestionService questionService;
    private GameService gameService;
//    private CaptService captService;

    // QuestionDto 조회
//    @GetMapping("")
//    public String getQuestion(Model model){
//        List<QuestionDto> questionDtoList = questionService.getList();
//        // 목록 가져오기
//        model.addAttribute("questionList", questionDtoList);
//        return questionDtoList.toString();
//    }

    // 이미지 파일 upload
    @PostMapping("")
    public RoomResponseDto upload(MultipartFile file,
                       @RequestParam String roomId,
                       @RequestBody QuestionRequestDto questionRequestDto) throws IOException {
        String imgUrl = s3UploadService.upload(file); // key : file
        CaptResponse caption = questionService.imgUrlPost(imgUrl);
        // Dto 변환 부분
        String audioUrl = s3UploadService.getAudioUrl(caption.getAudio());
        UserImage userImage = new UserImage(questionRequestDto.getUsername(), imgUrl, audioUrl, caption.getCaption());
        return gameService.addImageToRoom(roomId, userImage);
    }

    // random 이미지
    @GetMapping("/exam")
    public Optional<Question> findOne() {
        return questionRepository.randomQuestion();
    }
}
