package com.codingwasabi.bigtong.websocket.handler;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.service.AccountService;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final AccountService accountService;
    private String name;

    // account 와 websocketsession을 mapping 시킴
    private Map<String,WebSocketSession> webSocketSessionMap = new HashMap<>();

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
        chatService.message_in(session,chatMessage,webSocketSessionMap);


        /**
         *   ( 2 ) STOMP를 사용하게 되면, 방의 정보에 대한 값을 불러올 필요가 없다.
         */

        // ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        //chatMessage.setLeftPeople(chatRoom.leftPeople());

        //chatRoom.handleActions(session,chatMessage,chatService);
    }

    // connection이 close 되었을 때 , 비정상 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        // 접속된 session들에 대해
        webSocketSessionMap.forEach((nickname,webSocketSession)->{
            log.info("session for each test : " +webSocketSession.getId());

            // 종료된 session과 같다면 계정 정보 삭제
            if(webSocketSession == session) {
                accountService.deleteAccount(nickname,webSocketSessionMap);
                name= nickname;
            }

        });

        webSocketSessionMap.remove(name);

    }
}
