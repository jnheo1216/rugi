package com.b106.aipjt.domain.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserResponseDto {
    int score;
    String avatar;
    String nickname;
    boolean isSuper;

}
