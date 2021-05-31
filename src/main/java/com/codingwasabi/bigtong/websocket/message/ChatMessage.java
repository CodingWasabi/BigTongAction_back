package com.codingwasabi.bigtong.websocket.message;

import com.codingwasabi.bigtong.admin.entity.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMessage extends Message {


    private MessageType messageType ;

    private String nickname;

    private String message;

    // ENTER 할때 필요
    private RoomType roomType;

    // 남은 인원수
    private int leftPeople;


}
