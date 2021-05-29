package com.codingwasabi.bigtong.admin.entity;

import com.codingwasabi.bigtong.main.Account;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    // fetch 설정 필요
    @OneToMany(mappedBy = "chatRoom")
    private List<Account> accountList;

}
