package com.codingwasabi.bigtong.main.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class CurrentReturnDto {
    private int FRUIT;
    private int FISH;
    private int VEGETABLE;
    private int MEAT;
    private int GRAIN;
}
