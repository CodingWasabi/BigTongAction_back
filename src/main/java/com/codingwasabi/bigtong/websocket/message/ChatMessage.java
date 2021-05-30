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


    private MessageType type ;

    private String sender;

    private String message;

    // ENTER 할때 필요
    private RoomType roomType;

    // 남은 인원수
    private int leftPeople;

    // 밑은 알람을 위한 field 값들, 다른 객체로 빼줄 에정

    private String objectName;

    private int price;

    private LocalDateTime created;


}
