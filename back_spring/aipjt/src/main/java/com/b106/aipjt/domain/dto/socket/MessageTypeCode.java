package com.b106.aipjt.domain.dto.socket;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageTypeCode {
    CHAT,
    JOIN,
    LEAVE,
    ROOM_INFO,
    DRAWING,
    ERROR;
}
