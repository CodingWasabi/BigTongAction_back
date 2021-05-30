package com.codingwasabi.bigtong.common;

import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;


public class userTestFactory extends TestFactory {
    @Autowired
    protected AccountRepository accountRepository;

    protected Account account1;
    protected Account account2;

    @BeforeEach
    void beforeEach() throws Exception{
        account1 = Account.builder()
                .nickname("tset_user_daehwan")
                .build();

        account2 = Account.builder()
                .nickname("test_user_joonseo")
                .build();


    }
}
