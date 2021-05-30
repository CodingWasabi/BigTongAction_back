package com.codingwasabi.bigtong.main;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @CreatedDate
    @Column
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name="chatRoom_id")
    private ChatRoom chatRoom;

    @Column
    private String sessionId;

    @Column
    private String sessionIp;

    @Builder
    Account(String nickname){
        this.nickname = nickname;
    }

    @Transactional
    public void insertSession(String sessionId,String ip){

        this.sessionId = sessionId;
        this.sessionIp = ip;
    }

    public void enterRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }

    public void exitRoom(){
        this.sessionId = null;
        this.sessionIp = null;
        this.chatRoom = null;
    }
}
