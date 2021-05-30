package com.codingwasabi.bigtong.main.api.subject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Subject {


    @Column
    protected String bidtime;

    @Column
    protected String mclassname;

    @Column
    protected String price;

    @Column
    protected String unitname;
}
