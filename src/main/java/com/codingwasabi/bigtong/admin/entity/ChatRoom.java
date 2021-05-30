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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private RoomType type;

    // fetch 설정 필요
    @OneToMany(mappedBy = "chatRoom",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> accountList;

}
