package com.codingwasabi.bigtong.main.api.subject.exception;

public class NoDataInDBandInput extends RuntimeException{
    public NoDataInDBandInput (){
        super("저장된 데이터가 없으며, 입력된 데이터도 없습니다.");
    }
}
