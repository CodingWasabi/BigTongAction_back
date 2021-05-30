package com.codingwasabi.bigtong.websocket.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeftPeople {
    private int leftPeople;

    public LeftPeople(int leftPeople){
        this.leftPeople =leftPeople;
    }
}
