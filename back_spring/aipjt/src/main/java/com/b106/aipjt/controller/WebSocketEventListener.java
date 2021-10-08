package com.b106.aipjt.controller;

import com.b106.aipjt.domain.dto.socket.ChatMessageDto;
import com.b106.aipjt.domain.dto.socket.MessageTypeCode;
import com.b106.aipjt.domain.redishash.User;
import com.b106.aipjt.domain.repository.UserRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class WebSocketEventListener {

    private  static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessagingTemplate template;
    private final UserRedisRepository userRedisRepository;

    public WebSocketEventListener(SimpMessagingTemplate template, UserRedisRepository userRedisRepository) {
        this.template = template;
        this.userRedisRepository = userRedisRepository;
    }


    @EventListener
    public void onConnectEvent(SessionConnectedEvent connectedEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(connectedEvent.getMessage());
        logger.info("메시지내용" + headerAccessor.getSessionAttributes());
        String sessionId = headerAccessor.getSessionId();
        logger.info("세션아이디 " + sessionId);
        logger.info("Created New WebSocket Connection!");
//        logger.info("1" + headerAccessor.getAck());
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent disconnectEvent){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
//        headerAccessor.get();
        String roomId = headerAccessor.getSessionId();
//        logger.info("퇴장시 세션 어트리뷰트" + headerAccessor.getSessionAttributes());
//        if(username != null) {
//            logger.info("User Disconnected : " + username);
        logger.info("퇴장 이벤트 발생");
        ChatMessageDto messageDto = new ChatMessageDto();
        messageDto.setRoomId(roomId);
        System.out.println(messageDto.getRoomId());
        messageDto.setWriter("XXX");
        messageDto.setCode(MessageTypeCode.LEAVE);
//            messageDto.setWriter(username);
        messageDto.setMessage(" 나갔다!");

        template.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
        }

        @EventListener
        public void onUnsubscribe(SessionUnsubscribeEvent unsubscribeEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(unsubscribeEvent.getMessage());
        logger.info("구독취소 이벤트 발생"+headerAccessor.toString());
        }
    }

