package com.b106.aipjt.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChatRoomDTO {

    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoomDTO open(String roomId) {
        ChatRoomDTO chatroom = new ChatRoomDTO();

        chatroom.roomId = UUID.randomUUID().toString();
        return chatroom;
    }
}
