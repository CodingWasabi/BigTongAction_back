package com.codingwasabi.bigtong.websocket.handler;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.websocket.message.ChatMessage;
import com.codingwasabi.bigtong.websocket.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    /**
     * WebSocket이 연결 되었을때
     * @param session
     * @throws Exception
     */

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // test log
        log.info("websocket : handler : afterConnectionEstablished : well done");
        log.info("websocket info : " + session.getId());

    }


    /**
     * Client 가 Server로 메시지를 보냈을 때
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // JSON to Object
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(),ChatMessage.class);

        /**
         *   ( 1 ) 웹 소켓을 사용하여 Server가 메시지를 수신하는 방법
         */
        chatService.message_in(session,chatMessage);


        /**
         *   ( 2 ) STOMP를 사용하게 되면, 방의 정보에 대한 값을 불러올 필요가 없다.
         */

        // ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        //chatMessage.setLeftPeople(chatRoom.leftPeople());

        //chatRoom.handleActions(session,chatMessage,chatService);
    }

    // connection이 close 되었을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection END, "+session.getLocalAddress());
        // List<ChatRoom> chatRoomList = chatService.findAllRoom();

    }
}
