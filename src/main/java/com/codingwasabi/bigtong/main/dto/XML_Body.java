package com.codingwasabi.bigtong.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class XML_Body {
    public List<Item> items;
    public int numOfRows;
    public int pageNo;
    public int totalCount;
}
