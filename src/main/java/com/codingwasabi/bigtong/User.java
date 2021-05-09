package com.codingwasabi.bigtong;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nickname_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nickname;

    @CreatedDate
    @Column
    private LocalDateTime created;


    @Builder
    User(String nickname){
        this.nickname = nickname;
    }
}
