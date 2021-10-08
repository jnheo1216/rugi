package com.b106.aipjt.domain.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomSettingRequestDto {
    private int maxRound;
    private int roundTime;
    private int personnel;
}
