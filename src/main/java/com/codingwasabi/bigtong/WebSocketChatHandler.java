package com.codingwasabi.bigtong;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload= message.getPayload();
        Map<String, Object> httpSession = session.getAttributes();

        log.info("payload {} ", payload);
        log.info("this is " + session.getLocalAddress());
        log.info("remote addr : " + session.getRemoteAddress());
        //TextMessage textMessage = new TextMessage("Welcome to server\n");
        //session.sendMessage(textMessage);

        ChatMessage chatMessage = objectMapper.readValue(payload,ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session,chatMessage,chatService);

    }

}
