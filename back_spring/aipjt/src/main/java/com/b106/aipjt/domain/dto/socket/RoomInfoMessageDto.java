package com.b106.aipjt.domain.dto.socket;

import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomInfoMessageDto {
    private String roomId;
    private MessageTypeCode code = MessageTypeCode.ROOM_INFO;
    private RoomResponseDto message;
}
