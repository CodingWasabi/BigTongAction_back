package com.codingwasabi.bigtong;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private MessageType type ;
    private Long roomId;
    private String sender;
    private String message;

}
