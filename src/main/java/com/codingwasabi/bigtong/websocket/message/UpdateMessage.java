package com.codingwasabi.bigtong.websocket.message;

import com.codingwasabi.bigtong.admin.entity.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateMessage extends Message{
    private MessageType messageType;

    private RoomType roomType;

    private String mclassname;

    private String price;

    private String created;

}
