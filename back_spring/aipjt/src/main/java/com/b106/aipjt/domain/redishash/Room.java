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
    // 게임 상태를 나타낸다
    private String status = GameStatus.LOBBY.getValue();
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
    // 초기값 설정
    private int personnel = 8;
    // 최대 인원 설정 -> 어떻게 접근을 막을 것인가?? 방 입장 로직에서 처리해주어야 함


    // 게임에 참여한 유저 목록
    private List<User> userList = new ArrayList<>();


    public Room(String id, User superUser) {
        this.id = id;
        this.superUser = superUser;
    }
    // 게임 설정
    public Room gameSetting(int maxRound, int roundTime, int personnel) {
        this.setMaxRound(maxRound);
        this.setRoundTime(roundTime);
        this.setPersonnel(personnel);
        return this;
    }
    // 라운드 시작 전 설정
    public Room roundInit() {
        this.setCurrentRound(currentRound+1);
        this.setStatus(GameStatus.INIT.getValue());
        return this;
    }
    // 라운드 시작 설정
    public Room roundStart(Round round) {
        this.getRound().clear();
        this.getRound().add(round);
        this.setStatus(GameStatus.PLAY.getValue());
        return this;
    }

    public Room resetGame() {
        this.setStatus(GameStatus.LOBBY.getValue());
        this.setCurrentRound(0);
        this.getRound().clear();
        return this;
    }



    public boolean isFull() {
        // 유저리스트 >= 제한인원
        return userList.size()>=personnel;
    }

}
