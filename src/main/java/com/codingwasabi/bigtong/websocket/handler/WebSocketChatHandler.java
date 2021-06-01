package com.codingwasabi.bigtong.websocket.handler;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.api.APISerivce;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import com.codingwasabi.bigtong.main.service.AccountService;
import com.codingwasabi.bigtong.websocket.exception.ChatRoomNotExistException;
import com.codingwasabi.bigtong.websocket.message.ChatMessage;
import com.codingwasabi.bigtong.websocket.message.MessageType;
import com.codingwasabi.bigtong.websocket.message.UpdateMessage;
import com.codingwasabi.bigtong.websocket.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final APISerivce apiSerivce;
    private final ChatService chatService;
    private final AccountService accountService;
    private final ChatRoomRepository chatRoomRepository;
    private String name;

    // GRAIN,FRUIT,VEGETABLE,MEAT,FISH
    private final String[] GRAIN = {"01","03","04","05"};
    private final String[] FRUIT = {"06","09"};
    private final String[] VEGETABLE = {"10","11","12"};
    private final String[] MEAT = {"41","43"};
    private final String[] FISH = {"61","63"};

    // account 와 websocketsession을 mapping 시킴
    private Map<String,WebSocketSession> webSocketSessionMap = new HashMap<>();

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

            // 종료된 session과 같다면 계정 정보 삭제
            if(webSocketSession == session) {
                accountService.deleteAccount(nickname,webSocketSessionMap);
                name= nickname;
            }

        });

        webSocketSessionMap.remove(name);

    }

    public UpdateMessage makeUpdateMessage(Subject subject,RoomType roomType){
        UpdateMessage updateMessage = UpdateMessage.builder()
                .created(subject.getBidtime())
                .mclassname(subject.getMclassname())
                .price(subject.getPrice())
                .roomType(roomType)
                .messageType(MessageType.NOTICE)
                .build();

        return updateMessage;
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_grain(){

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(RoomType.GRAIN)
                .orElseThrow(ChatRoomNotExistException::new);

        // 업데이트가 되었다면 ws 로 데이터 전송
        List<Subject> grainList = apiSerivce.manageSubject(GRAIN,"GRAIN");

        for(Subject subject : grainList){
            chatService.sendMessageAll(makeUpdateMessage(subject,RoomType.GRAIN),chatRoom,webSocketSessionMap);
        }
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_fruit(){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(RoomType.FRUIT)
                .orElseThrow(ChatRoomNotExistException::new);
        // 업데이트가 되었다면 ws 로 데이터 전송
        List<Subject> fruitList = apiSerivce.manageSubject(FRUIT,"FRUIT");


        for(Subject subject : fruitList){
            chatService.sendMessageAll(makeUpdateMessage(subject,RoomType.FRUIT),chatRoom,webSocketSessionMap);
        }

    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_fish(){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(RoomType.FISH)
                .orElseThrow(ChatRoomNotExistException::new);
        // 업데이트가 되었다면 ws 로 데이터 전송
        List<Subject> fishList = apiSerivce.manageSubject(FISH,"FISH");

        for(Subject subject:fishList)
            chatService.sendMessageAll(makeUpdateMessage(subject,RoomType.FISH),chatRoom,webSocketSessionMap);
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_vegetable(){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(RoomType.VEGETABLE)
                .orElseThrow(ChatRoomNotExistException::new);
        // 업데이트가 되었다면 ws 로 데이터 전송
        List<Subject> vegetableList = apiSerivce.manageSubject(VEGETABLE,"VEGETABLE");

        for(Subject subject:vegetableList)
            chatService.sendMessageAll(makeUpdateMessage(subject,RoomType.VEGETABLE),chatRoom,webSocketSessionMap);
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_meat(){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByType(RoomType.MEAT)
                .orElseThrow(ChatRoomNotExistException::new);
        // 업데이트가 되었다면 ws 로 데이터 전송
        List<Subject> meatList = apiSerivce.manageSubject(MEAT,"MEAT");

        for(Subject subject:meatList)
            chatService.sendMessageAll(makeUpdateMessage(subject,RoomType.MEAT),chatRoom,webSocketSessionMap);

    }
}
