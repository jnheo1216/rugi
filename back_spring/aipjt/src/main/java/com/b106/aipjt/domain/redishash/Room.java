package com.b106.aipjt.domain.redishash;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@RedisHash("room")
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Room {
    @Id
    private String id;
    // 방장
    private User superUser;
    // 게임이 시작한 경우 isStart는 true
    private boolean isStart = false;
    // 게임이 시작되었지만 라운드가 진행중이지 않은 사이시간인 경우 isPlay는 false
    // 라운드가 진행중이면 isPlay는 true
    private boolean isPlay = false;
    // 라운드 객체를 넣지 않고 저장할 경우 어떻게 되는지 확인 필요
    // 라운드가 진행중이지 않은 경우 객체가 없는 것을 예상하고 있음
    // Optional이 안먹어서 List로 야매로 해야됨
    private List<Round> round = new ArrayList<>();
    // 현재 라운드 : 현재 몇 라운드가 진행중인지
    private int currentRound = 0;

    // 게임 설정 라운드
    private int maxRound = 3;
    // 게임 설정 시간
    private int roundTime = 60;
    // 게임에 참여한 유저 목록
    private List<User> userList = new ArrayList<>();


    public Room(String id, User superUser) {
        this.id = id;
        this.superUser = superUser;
    }
}
