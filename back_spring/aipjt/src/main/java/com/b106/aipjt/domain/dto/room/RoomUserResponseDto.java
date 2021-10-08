package com.b106.aipjt.domain.dto.room;

import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserResponseDto {
    int score;
    String avatar;
    String nickname;
    boolean isSuper;

    public static List<RoomUserResponseDto> of(Room room) {
        List<RoomUserResponseDto> roomUsers = new ArrayList<>();
        room.getUserList().forEach(u -> {
            roomUsers.add(new RoomUserResponseDto(0, u.getAvatar(), u.getNickname(), room.getSuperUser().getId().equals(u.getId())));
        });
        return roomUsers;
    }

}
