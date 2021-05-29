package com.codingwasabi.bigtong.websocket.service;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import com.codingwasabi.bigtong.websocket.message.ChatMessage;
import com.codingwasabi.bigtong.websocket.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final AccountRepository accountRepository;

    // account 와 websocketsession을 mapping 시킴
    private Map<Account,WebSocketSession> webSocketSessionMap = new HashMap<>();

    @Transactional
    public void message_in(WebSocketSession webSocketSession, ChatMessage chatMessage){

        //test log
        log.info("websocket : chatService : message_in ");

        MessageType messageType = chatMessage.getType();
        RoomType roomType = chatMessage.getRoomType();
        String nickname = chatMessage.getSender();

        Account account = accountRepository.findAccountByNickname(nickname);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(roomType);

        // chatMessage 의 Type을 구분

        // ENTER 인경우
        if(messageType == MessageType.ENTER){


            // inetSocketAddress 형태의 주소를 String형태로 캐스팅
            // inetSocketAddress -> inetAddress : get String ip
            account.insertSession(webSocketSession.getId(),
                    webSocketSession.getRemoteAddress().getAddress().getHostAddress());
            account.enterRoom(chatRoom);

            // 해당 account 와 session 매핑
            webSocketSessionMap.put(account,webSocketSession);

            ChatMessage message = ChatMessage.builder()
                    .message(account.getNickname()+"님이 입장하셨습니다.")
                    .sender("ADMIN")
                    .type(MessageType.NOTICE)
                    .build();

            // 해당 방에 입장 메시지 전송
            sendMessageAll(message,chatRoom);
        }

        // TALK 이거나 NOTICE 인 경우
        if(messageType == MessageType.TALK || messageType == MessageType.NOTICE)
            sendMessageAll(chatMessage,chatRoom);

    }

    // Server -> Client
    // (Actually, Client -> Server -> Client)
    public void sendMessageAll(ChatMessage chatMessage,ChatRoom chatRoom){

        // test log
        log.info("websocket : chatService : sendMessageAll ");

        // 해당 방에 접속된 모든 Account 리스트 생성
        List<Account> accountList = accountRepository.findAllByChatRoomId(chatRoom.getId());

        // 각 Account 에게 메시지 전송
        if(accountList.isEmpty())
            log.info("account is Empty");
        else
        for(Account account : accountList){
            // 각 Account 별 websocketSession 정보 조회
            WebSocketSession webSocketSession = webSocketSessionMap.get(account);

            log.info("send all , session info : "+webSocketSession.getId());

            // 해당 webSocketSession에 메시지 전송
            sendMessage(webSocketSession,chatMessage);
        }
    }

    public void sendMessage(WebSocketSession webSocketSession, ChatMessage chatMessage){
        try{
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }catch (IOException i){
            log.error(i.getMessage(),i);
        }
    }
}
