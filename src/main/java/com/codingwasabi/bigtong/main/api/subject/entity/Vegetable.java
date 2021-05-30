package com.codingwasabi.bigtong.main.api.subject.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Vegetable extends Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Vegetable(String bidtime,String mclassname, String price, String unitname){
        this.bidtime = bidtime;
        this.mclassname = mclassname;
        this.price = price;
        this.unitname = unitname;
    }
}
