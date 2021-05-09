package com.codingwasabi.bigtong.common;

import com.codingwasabi.bigtong.User;
import com.codingwasabi.bigtong.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class userTestFactory {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;



    protected User user1;
    protected User user2;

    @BeforeEach
    void beforeEach() throws Exception{
        user1 = User.builder()
                .nickname("tset_user_daehwan")
                .build();

        user2 = User.builder()
                .nickname("test_user_joonseo")
                .build();


    }
}
