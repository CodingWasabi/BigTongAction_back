package com.codingwasabi.bigtong.admin.entity;

import com.codingwasabi.bigtong.Account;
import com.codingwasabi.bigtong.websocket.ChatService;
import com.codingwasabi.bigtong.websocket.model.message.ChatMessage;
import com.codingwasabi.bigtong.websocket.model.message.MessageType;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    // fetch 설정 필요
    @OneToMany(mappedBy = "chatRoom")
    private List<Account> accountList;



/*
    @Transactional
    public int handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){

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
*/

/*
    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
    }*/
}
