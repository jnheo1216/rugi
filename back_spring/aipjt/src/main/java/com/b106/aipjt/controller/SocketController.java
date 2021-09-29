package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.socket.ChatMessageDto;
import com.b106.aipjt.domain.dto.socket.ImageMessageDto;
import com.b106.aipjt.domain.dto.socket.MessageTypeCode;
import com.b106.aipjt.domain.dto.socket.RoomInfoMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // prefix로 pub 설정이 되어있으므로 /pub/chat/enter로 퍼블리쉬 요청이 오는 것
    @MessageMapping(value = "/chat/enter")
    public void enterRoom(ChatMessageDto messageDto) {
        System.out.println(messageDto.getRoomId());
        messageDto.setMessage("두둥 등장!");
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto messageDto) {
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value = "/chat/exit")
    public void exitRoom(ChatMessageDto messageDto) {
        System.out.println(messageDto.getRoomId());
        messageDto.setMessage(" 나갔다!");
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    // /pub/room/info로 퍼블리쉬 요청오면 client.send 오면 현재 방 정보 전체에게 퍼블리쉬
    @MessageMapping(value =  "/room/info")
    public void roomInfo(RoomInfoMessageDto messageDto) {
        // 방장인지 체크하고 맞으면 뿌려주는 로직 추가
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value =  "/image")
    public void imageCaption(ImageMessageDto messageDto) {
        // 이미지 전달되면 실행될 메서드 구현 필요
        // template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }
}