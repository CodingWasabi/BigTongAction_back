package com.codingwasabi.bigtong.main.repository;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.main.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    List<Account> findAll();
    Optional<Account> findByNickname(String nickname);

    Account findAccountByNickname(String nickname);

    List<Account> findAllByChatRoomId(Long id);

    void deleteAccountByNickname(String nickname);
}
