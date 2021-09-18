package com.b106.aipjt.domain.dto.room;

import com.b106.aipjt.domain.redishash.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponseDto {
    private String id;
    private String superUser;
    private boolean isStart = false;
    private int round = 0;
    private int maxRound = 3;
    private int roundTime = 60;
    private List<UserResponseDto> memberList = new ArrayList();

    public RoomResponseDto(String id, User user) {
        this.id = id;
        this.superUser = user.getId();
        this.memberList.add(new UserResponseDto(user.getId(), user.getAvatar(), user.getNickname()));
    }
}
