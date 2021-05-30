package com.codingwasabi.bigtong.main.api.subject.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bidtime;

    @Column
    private String mclassname;

    @Column
    private String price;

    @Column
    private String unitname;
}
