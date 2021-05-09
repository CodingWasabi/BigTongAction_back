package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.common.userTestFactory;
import com.codingwasabi.bigtong.main.service.MainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MainControllerTest extends userTestFactory {

    @Autowired
    MainService mainService;

    @Test
    @DisplayName("MAIN > 닉네임중복확인")
    void MAIN_닉네임중복확인 () throws Exception{
        // given
        // beforeEach()

        // when
        userRepository.save(user1);
        userRepository.save(user2);

        // then
        this.mockMvc.perform(post("/main").param("nickname","test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}