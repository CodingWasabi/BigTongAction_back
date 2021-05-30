package com.codingwasabi.bigtong.main.api.subject.exception;

public class NoPreviousDataInTable extends RuntimeException{
    public NoPreviousDataInTable(){
        super("현재 API 에서 불러온 데이터가 없어서, DB에 저장된 값이 없습니다.");

    }
}
