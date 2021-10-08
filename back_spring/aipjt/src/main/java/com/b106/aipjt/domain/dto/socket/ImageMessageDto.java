package com.b106.aipjt.domain.dto.socket;

import com.b106.aipjt.domain.dto.room.RoomResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageMessageDto {
    private String roomId;
    private MessageTypeCode code = MessageTypeCode.DRAWING;
    private String writer;
    private RoomResponseDto message;
}
