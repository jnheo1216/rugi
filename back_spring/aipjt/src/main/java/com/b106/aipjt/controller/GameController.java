package com.b106.aipjt.controller;


import com.b106.aipjt.domain.dto.ResponseDto;
import com.b106.aipjt.service.GameService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/game")
public class GameController {

    // 게임 시작과 진행 역할을 맡는 컨트롤러
    private final GameService gameService;
    // 게임 시작: 게임을 시작하는 요청 : 요청 종류는?
    // 방장인지부터 확인 검증
    @PostMapping("")
    public ResponseDto<String> gameStart(@RequestHeader(value="User-Id") String userId,
                                         @RequestParam String roomId) throws InterruptedException {
        gameService.gameStart(userId, roomId); // 비동기 요청 여기서 요청하는게 맞아보임
        // 문제가 없다면 여기서 끝까지 돌아간다. 비동기이므로 응답은 먼저 보내짐
        return new ResponseDto<>(HttpStatus.OK.value(), "요청 수신 성공", "");
    }

    // 스킵객체 넘겨줘서 삭제부터 해줘야함
    @PostMapping("/skip")
    public ResponseDto<String> gameSkip(@RequestHeader(value="User-Id") String userId,
                                        @RequestParam String roomId,
                                        @RequestBody SkipRequestDto skipRequestDto) throws InterruptedException {
        log.error("========================스킵 시작===========================");
        log.error("========================스킵 삭제 시작===========================");
        gameService.skipRound(skipRequestDto.getSkipId());
        log.error("========================스킵 삭제 완료===========================");
        log.error("========================라운드 시작 호출 전===========================");
        gameService.gameStart(userId, roomId); // 비동기 요청 여기서 요청하는게 맞아보임
        log.error("========================라운드 시작 호출 후===========================");
        // 문제가 없다면 여기서 끝까지 돌아간다. 비동기이므로 응답은 보내짐
        log.error("========================컨트롤러 리턴===========================");
        return new ResponseDto<>(HttpStatus.OK.value(), "요청 수신 성공", "");
    }

    @Getter
    @NoArgsConstructor @AllArgsConstructor
    @ToString
    static class SkipRequestDto {
        private String skipId;
    }

}
