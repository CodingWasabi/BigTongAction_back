package com.codingwasabi.bigtong.handler;

import com.codingwasabi.bigtong.ChatMessage;
import com.codingwasabi.bigtong.ChatRoom;
import com.codingwasabi.bigtong.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private ChatService chatService;
    private Map<Long,Integer> peopleInRoom = new HashMap<>();

    // connection 이 연결 되었을때, client의 연결이 성공했을때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connect OK, "+session.getLocalAddress());
    }



    // socket에 message를 보냈을 때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // JSON to Object
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(),ChatMessage.class);
        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());


        chatRoom.handleActions(session,chatMessage);
    }

    // connection이 close 되었을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection END, "+session.getLocalAddress());
    }
}
