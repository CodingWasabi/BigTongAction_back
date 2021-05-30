package com.codingwasabi.bigtong.main.api.subject.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Fish extends Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Fish(String bidtime,String mclassname, String price, String unitname){
        this.bidtime = bidtime;
        this.mclassname = mclassname;
        this.price = price;
        this.unitname = unitname;
    }
}
