package com.b106.aipjt.domain.dto.room;

import com.b106.aipjt.domain.redishash.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomResponseDto {
    private String id;
    private String superUser;
    private String status;
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



    public static RoomResponseDto toRoom(Room room, List<RoomUserResponseDto> userListDto) {
        return RoomResponseDto.builder().id(room.getId())
            .superUser(room.getSuperUser().getId())
            .status(room.getStatus())
            .currentRound(room.getCurrentRound())
            .maxRound(room.getMaxRound())
            .roundTime(room.getRoundTime())
            .personnel(room.getPersonnel())
            .userList(userListDto)
            .build();
    }

}
