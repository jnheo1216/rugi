package com.b106.aipjt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Stomp 사용을 위한 어노테이션
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket(또는 SockJS) 클라이언트가 WebSocket 핸드셰이크를 위해
        // 연결해야 하는 엔드포인트의 HTTP URL
        registry.addEndpoint("/stomp/chat").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/stomp/room").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 헤더의 데스티네이션이 /pub으로  시작하는 메서드로 메시지가 @MessageMapping으로 메서드 라우팅 됨
        // 이 메시지를 브로드캐스팅 해줘
        config.setApplicationDestinationPrefixes("/pub");
        // 구독과 브로드캐스팅에 내장된 심플브로커를 이용
        // 이 해당 브로커로 데스티네이션주소가 시작되는 클라이언트에 메시지를 라우팅
        // 나 이 토픽을 구독할래
        config.enableSimpleBroker("/sub");
    }
}
