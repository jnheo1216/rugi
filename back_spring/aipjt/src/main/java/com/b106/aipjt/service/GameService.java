package com.b106.aipjt.service;

import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.Round;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import com.b106.aipjt.domain.repository.RoundRedisRepository;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final SimpMessagingTemplate template;
    private final UserRedisRepository userRedisRepository;
    private final RoundRedisRepository roundRedisRepository;
    private final RoomRedisRepository roomRedisRepository;
    private final String ROOM_PREFIX = "room/";


    // 에러 발생 시 캐치해서 방장한테만 알려주기 위해 동기로 동작하도록 작성

    public boolean gameExecute(String userId, String roomId) {
        // 게임 관련 설정은 여기서 하지 않고, 다른 요청에서 처리하도록 해준다.
        // User와 Room 객체를 가져온다.
        Optional<User> userById = userRedisRepository.findById(userId);
        Optional<Room> roomById = roomRedisRepository.findById(roomId);
        // 검증한다.
        if (userById.isEmpty()) throw new RuntimeException("유저가 존재하지 않습니다");
        if (roomById.isEmpty()) throw new RuntimeException("방이 존재하지 않습니다");
        // 꺼낸다
        User user = userById.get();
        Room room = roomById.get();
        // 검증한다
        if (!user.getId().equals(room.getSuperUser().getId())) throw new RuntimeException("방장만 게임을 시작할 수 있습니다");
        if (room.isStart()) throw new RuntimeException("이미 게임이 진행중입니다");
        // 게임 시작 적용을 위한 Room의 값 변경 후 save
        roomRedisRepository.save(room.gameStart());
        return true;
    }

    // 여기는 for 문과 Skip 객체를 사용해서 정해진 만큼의 라운드를 수행한다.
    // Skip 객체가 사라졌을 경우 쓰레드를 종료한다
    // 여기 정말 세심하게 짜야함
    @Async
    public void roundStart(String userId, String roomId) throws InterruptedException {
        // 우선 방장인지부터 확인한다.

        // 현재 라운드부터 반복문을 돌기 시작한다.

        // 모든 라운드가 종료되었다면 결과를 뿌리고 종료한다
    }
    // 가장 큰 문제. 질문에 대한 정보는 어디에서 가져오나? DB에서 가져오지. 그럼 랜덤으로 가져오나? ㅇㅇ
    // 랜덤으로 가져온 질문에 대한 답은 어디에 저장하나?? Round에 저장하기. 어차피 백에서 지워져도 프론트에서 저장하고 있으므로
    // 하지만 직접적인 url을 가지고 있으면 안된다. 참조할 수 있는 데이터를 가지고 있어야 함.
}
