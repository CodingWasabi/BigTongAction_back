package com.codingwasabi.bigtong;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {

    private MessageType type ;
    private Long roomId;
    private String sender;
    private String message;
    private int leftPeople;

    private RoomType roomType;
    private String objectName;
    private int price;
    private LocalDateTime created;

    public void insert(){
        this.leftPeople++;
    }

    public void remove(){
        this.leftPeople--;
    }

}
