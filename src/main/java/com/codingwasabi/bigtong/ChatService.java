package com.codingwasabi.bigtong;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Getter
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<Long,ChatRoom> chatRooms;
    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(Long roomId){
        return chatRooms.get(roomId);
    }

    public ChatRoom createChatRoom(String name){
        Long randomId= (long)chatRooms.size()+1;

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();

        chatRooms.put(randomId,chatRoom);

        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }
}
