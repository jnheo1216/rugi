package com.b106.aipjt.domain.dto.room;

import com.b106.aipjt.domain.redishash.User;
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
    @Builder.Default
    private boolean isStart = false;
    @Builder.Default
    private int round = 0;
    @Builder.Default
    private int maxRound = 3;
    @Builder.Default
    private int roundTime = 60;
    @Builder.Default
    private List<RoomUserResponseDto> userList = new ArrayList<>();

}
