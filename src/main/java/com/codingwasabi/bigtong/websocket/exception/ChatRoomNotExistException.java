package com.codingwasabi.bigtong.websocket.exception;

public class ChatRoomNotExistException extends RuntimeException{
    public ChatRoomNotExistException(){
        super("방이 아직 존재하지 않습니다.");
    }
}
