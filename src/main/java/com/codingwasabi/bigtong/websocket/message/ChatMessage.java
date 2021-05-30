package com.codingwasabi.bigtong.websocket.message;

import com.codingwasabi.bigtong.admin.entity.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessage{


    private MessageType messageType ;

    private String sender;

    private String message;

    // ENTER 할때 필요
    private RoomType roomType;

    // 남은 인원수
    private int leftPeople;


}
