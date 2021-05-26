package com.codingwasabi.bigtong;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nickname_table")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @CreatedDate
    @Column
    private LocalDateTime created;


    @Builder
    Account(String nickname){
        this.nickname = nickname;
    }
}
