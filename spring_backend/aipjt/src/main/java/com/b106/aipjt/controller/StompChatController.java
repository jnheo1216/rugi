package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    // prefix로 topic이 설정이 되어있으므로 /topic/chat/enter로 퍼블리쉬 요청이 오는 것
    @MessageMapping(value = "/chat/enter")
    public void enterRoom(ChatMessageDTO message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
