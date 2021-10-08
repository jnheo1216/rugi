package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.socket.ChatMessageDto;
import com.b106.aipjt.domain.dto.socket.ImageMessageDto;
import com.b106.aipjt.domain.dto.socket.MessageTypeCode;
import com.b106.aipjt.domain.dto.socket.RoomInfoMessageDto;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessagingTemplate template;
    private final RoomRedisRepository roomRedisRepository;
    private final UserRedisRepository userRedisRepository;

    // prefix로 pub 설정이 되어있으므로 /pub/chat/enter로 퍼블리쉬 요청이 오는 것
    @MessageMapping(value = "/chat/enter")
    public void enterRoom(ChatMessageDto messageDto) {
        System.out.println("방 번호: " + messageDto.getRoomId());
        messageDto.setCode(MessageTypeCode.JOIN);
        messageDto.setMessage("두둥 등장!");
//        Optional<User> newPlayer = userRedisRepository.findByNickname(messageDto.getWriter());
//        if (newPlayer.isPresent()){
//            newPlayer.get().setSessionId();
//        }
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto messageDto) {
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        messageDto.setCode(MessageTypeCode.CHAT);
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value = "/chat/exit")
    public void exitRoom(ChatMessageDto messageDto) {
        System.out.println(messageDto.getRoomId());
        messageDto.setCode(MessageTypeCode.LEAVE);
        messageDto.setMessage(" 나갔다!");
        // /pub/chat/room/{roomId}를 구독하는 애들한테 메시지를 퍼블리쉬
        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    // /pub/room/info로 퍼블리쉬 요청오면 client.send 오면 현재 방 정보 전체에게 퍼블리쉬
    @MessageMapping(value = "/room/info")
    public void roomInfo(RoomInfoMessageDto messageDto) {
        // 방장인지 체크하고 맞으면 뿌려주는 로직 추가 레디스 서비스 단에서 처리중
        template.convertAndSend("/sub/room_info/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping(value =  "/image")
    public void imageCaption(ImageMessageDto messageDto) {
        // 이미지 전달되면 실행될 메서드 구현 필요
        // 해당 룸에 참여한 사용자들을 위한 이미지가
         template.convertAndSend("/sub/image/room/" + messageDto.getRoomId(), messageDto);
    }
}
