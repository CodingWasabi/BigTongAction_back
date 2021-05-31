package com.codingwasabi.bigtong.websocket.service;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import com.codingwasabi.bigtong.websocket.exception.AccountNotExistException;
import com.codingwasabi.bigtong.websocket.exception.ChatRoomNotExistException;
import com.codingwasabi.bigtong.websocket.message.*;
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


    @Transactional
    public void message_in(WebSocketSession webSocketSession, ChatMessage chatMessage,Map<String,WebSocketSession> webSocketSessionMap){


        MessageType messageType = chatMessage.getMessageType();
        RoomType roomType = chatMessage.getRoomType();
        String nickname = chatMessage.getNickname();

        log.info("ENTER chatmessage : " + chatMessage);
        log.info("ENTER chatmessage 내용 : " + chatMessage.getMessage() );
        log.info("ENTER : " + nickname);

        Account account = accountRepository.findByNickname(nickname).orElseThrow(AccountNotExistException::new);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(roomType).orElseThrow(ChatRoomNotExistException::new);

        // ENTER 인경우
        if(messageType == MessageType.ENTER){
            log.info("ENTER_in : " + nickname);

            // inetSocketAddress 형태의 주소를 String형태로 캐스팅
            // inetSocketAddress -> inetAddress : get String ip
            account.insertSession(webSocketSession.getId(),
                    webSocketSession.getRemoteAddress().getAddress().getHostAddress());
            account.enterRoom(chatRoom);

            chatRoomRepository.flush();

            // 해당 account 와 session 매핑
            webSocketSessionMap.put(account.getNickname(),webSocketSession);

            ChatMessage message = ChatMessage.builder()
                    .message(account.getNickname()+"님이 입장하셨습니다.")
                    .nickname("ADMIN")
                    .leftPeople(chatRoom.getAccountList().size())
                    .roomType(account.getChatRoom().getType())
                    .messageType(MessageType.TALK)
                    .build();

            log.info("보내는 메시지 확인 : "+message);
            log.info("보내는 메시지 내용 : "+message.getMessage());

            // 해당 방에 입장 메시지 전송
            sendMessageAll(message,chatRoom,webSocketSessionMap);
        }

        // TALK 이거나 NOTICE 인 경우
        else if(messageType == MessageType.TALK || messageType == MessageType.NOTICE){
            chatMessage.setLeftPeople(chatRoom.getAccountList().size());
            sendMessageAll(chatMessage,chatRoom,webSocketSessionMap);
        }

        // EXIT 인경우
        else if (messageType == MessageType.EXIT){
            // account 에서 chatroom 삭제
            ChatRoom room = account.getChatRoom();
            account.exitRoom();

            // map 에서 삭제
            webSocketSessionMap.remove(account);
            sendMessageAll(new LeftPeople(chatRoom.getAccountList().size()),chatRoom,webSocketSessionMap);
            chatRoomRepository.flush();

        }
    }

    /**
     * 방에 접속한 사람들에게 메시지 전체 전송
     * @param message
     * @param chatRoom
     * @param webSocketSessionMap
     */

    // Server -> Client
    // (Actually, Client -> Server -> Client)
    public void sendMessageAll(Message message,ChatRoom chatRoom,Map<String,WebSocketSession> webSocketSessionMap){

        // 해당 방에 접속된 모든 Account 리스트 생성
        // error 이거나
        List<Account> accountList = accountRepository.findAllByChatRoomId(chatRoom.getId())
                .orElseThrow(AccountNotExistException::new);;

        // 각 Account 에게 메시지 전송
        if(accountList.isEmpty());
        else
        for(Account account : accountList){
            // 각 Account 별 websocketSession 정보 조회
            // error 이거나
            WebSocketSession webSocketSession = webSocketSessionMap.get(account.getNickname());

            // 해당 webSocketSession에 메시지 전송
            sendMessage(webSocketSession,message);
        }
    }

    /**
     * 메시지 하나씩 보내기
     * @param webSocketSession
     * @param message
     */

    public void sendMessage(WebSocketSession webSocketSession, Message message){

        log.info("message check : "+ message);
        try{
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException i){
            log.error(i.getMessage(),i);
        }
    }
}
