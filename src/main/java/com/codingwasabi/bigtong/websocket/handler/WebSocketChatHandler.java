package com.codingwasabi.bigtong.websocket.handler;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.api.APISerivce;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import com.codingwasabi.bigtong.main.service.AccountService;
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
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final APISerivce apiSerivce;
    private final ChatService chatService;
    private final AccountService accountService;
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
            log.info("session for each test : " +webSocketSession.getId());

            // 종료된 session과 같다면 계정 정보 삭제
            if(webSocketSession == session) {
                accountService.deleteAccount(nickname,webSocketSessionMap);
                name= nickname;
            }

        });

        webSocketSessionMap.remove(name);

    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_grain(){

        log.info("update grain in ");
        // 업데이트가 되었다면 ws 로 데이터 전송
        Subject grain = apiSerivce.manageSubject(GRAIN,"GRAIN");

        if(grain != null){
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .created(grain.getBidtime())
                    .mclassname(grain.getMclassname())
                    .price(grain.getPrice())
                    .roomType(RoomType.GRAIN)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
        else{
            log.info("GRAIN null 진입");
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .mclassname("did not update")
                    .roomType(RoomType.GRAIN)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_fruit(){

        // 업데이트가 되었다면 ws 로 데이터 전송
        Subject fruit = apiSerivce.manageSubject(FRUIT,"FRUIT");

        if(fruit != null){
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .created(fruit.getBidtime())
                    .mclassname(fruit.getMclassname())
                    .price(fruit.getPrice())
                    .roomType(RoomType.FRUIT)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
        else{
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .mclassname("did not update")
                    .roomType(RoomType.FRUIT)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_fish(){

        // 업데이트가 되었다면 ws 로 데이터 전송
        Subject fish = apiSerivce.manageSubject(FISH,"FISH");

        if(fish != null){
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .created(fish.getBidtime())
                    .mclassname(fish.getMclassname())
                    .price(fish.getPrice())
                    .roomType(RoomType.FISH)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
        else{
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .mclassname("did not update")
                    .roomType(RoomType.FISH)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_vegetable(){

        // 업데이트가 되었다면 ws 로 데이터 전송
        Subject vegetable = apiSerivce.manageSubject(VEGETABLE,"VEGETABLE");

        if(vegetable != null){
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .created(vegetable.getBidtime())
                    .mclassname(vegetable.getMclassname())
                    .price(vegetable.getPrice())
                    .roomType(RoomType.VEGETABLE)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
        else{
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .mclassname("did not update")
                    .roomType(RoomType.VEGETABLE)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
    }

    // 60초 마다 실행 test
    @Scheduled(fixedDelay =60* 1000)
    public void renewList_meat(){

        // 업데이트가 되었다면 ws 로 데이터 전송
        Subject meat = apiSerivce.manageSubject(MEAT,"MEAT");

        if(meat != null){
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .created(meat.getBidtime())
                    .mclassname(meat.getMclassname())
                    .price(meat.getPrice())
                    .roomType(RoomType.MEAT)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
        else{
            UpdateMessage updateMessage = UpdateMessage.builder()
                    .mclassname("did not update")
                    .roomType(RoomType.MEAT)
                    .messageType(MessageType.NOTICE)
                    .build();
            chatService.noticeRoomPeople(updateMessage,webSocketSessionMap);
        }
    }
}
