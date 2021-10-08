package com.b106.aipjt.domain.dto.room;

import com.b106.aipjt.domain.redishash.Quiz;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.Round;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomResponseDto {
    private String id;
    private String superUser;
    private String status;
    private Long timestamp;
    private int remain; // 남은 시간 넣을 것 : Default인가?
    @Builder.Default
    private int currentRound = 0;
    @Builder.Default
    private int maxRound = 3;
    @Builder.Default
    private int roundTime = 60;
    @Builder.Default
    private int personnel = 8;
    @Builder.Default
    private List<RoomUserResponseDto> userList = new ArrayList<>();
    @Builder.Default
    private List<Quiz> quizList = new ArrayList<>();




    public static RoomResponseDto toRoom(Room room, List<RoomUserResponseDto> userListDto) {

        RoomResponseDto build = RoomResponseDto.builder().id(room.getId())
            .superUser(room.getSuperUser().getId())
            .status(room.getStatus())
            .currentRound(room.getCurrentRound())
            .maxRound(room.getMaxRound())
            .roundTime(room.getRoundTime())
            .personnel(room.getPersonnel())
            .userList(userListDto)
            .timestamp(room.getTimestamp())
            .quizList(room.getQuizList())
            .build();

        Optional<Round> optionalRound = Optional.ofNullable(room.getRound());
        if (optionalRound.isPresent()) {
            build.setRemain(optionalRound.get().getTimeout());
        }
        log.error(room.toString());
        log.error(build.toString());
        return build;
    }

}
