package com.codingwasabi.bigtong;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ChatRoom {

    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(Long roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public int leftPeople(){
        return sessions.size();
    }

    @Transactional
    public int handleActions(WebSocketSession session, ChatMessage chatMessage,ChatService chatService){

        if(chatMessage.getType().equals(MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장했습니다.\n");
            chatMessage.insert();

        }
        else if (chatMessage.getType().equals(MessageType.EXIT)){
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 퇴장하셨습니다.\n");
            chatMessage.remove();

        }
        sendMessage(chatMessage,chatService);

        return sessions.size();
    }

    @Transactional
    public int endAbnormal(WebSocketSession session){
        sessions.remove(session);

        return sessions.size();
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
    }
}
