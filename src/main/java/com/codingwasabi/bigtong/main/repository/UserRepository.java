package com.codingwasabi.bigtong.main.repository;

import com.codingwasabi.bigtong.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Account,Long> {

    List<Account> findAll();

    Optional<Account> findByNickname(String nickname);
}
