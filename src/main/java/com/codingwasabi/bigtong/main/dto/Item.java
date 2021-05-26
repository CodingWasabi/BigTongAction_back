package com.codingwasabi.bigtong.main.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Item {
    public String bidtime;
    public String coname;
    public String marketname;
    public String mclassname;
    public String price;
    public String rnum;
    public String sanji;
    public String sclassname;
    public String tradeamt;
    public String unitname;
}
