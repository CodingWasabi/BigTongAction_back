package com.codingwasabi.bigtong.main.api.subject.entity;

import javax.persistence.*;

@Entity
public class Fish extends Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
