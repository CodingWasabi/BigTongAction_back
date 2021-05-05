package com.codingwasabi.bigtong;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ChatRoom {

    private ChatService chatService;
    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
    private Map<Long,Integer> peopleInRoom;

    @Builder
    public ChatRoom(Long roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    @Transactional
    public void handleActions(WebSocketSession session, ChatMessage chatMessage){

        int peopleLeft= peopleInRoom.get(chatMessage.getRoomId());

        if(chatMessage.getType().equals(MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장했습니다.\n");

            peopleInRoom.replace(chatMessage.getRoomId(),peopleLeft+1);

        }
        else if (chatMessage.getType().equals(MessageType.EXIT)){
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 퇴장하셨습니다.\n");

            peopleInRoom.replace(chatMessage.getRoomId(),peopleLeft-1);

        }
        sendMessage(chatMessage,chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
    }
}
