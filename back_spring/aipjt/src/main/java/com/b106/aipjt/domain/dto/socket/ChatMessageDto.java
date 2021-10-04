package com.b106.aipjt.domain.dto.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String roomId;
    private MessageTypeCode code;
    private String writer;
    private String message;
}
