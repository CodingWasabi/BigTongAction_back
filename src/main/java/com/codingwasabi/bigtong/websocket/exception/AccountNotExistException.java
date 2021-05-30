package com.codingwasabi.bigtong.websocket.exception;

public class AccountNotExistException extends RuntimeException{
    public AccountNotExistException(){
        super("아직 유저가 존재하지 않습니다.");
    }
}
