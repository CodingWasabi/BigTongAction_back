package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.common.userTestFactory;
import com.codingwasabi.bigtong.main.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends userTestFactory {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("MAIN > 닉네임중복확인")
    void MAIN_닉네임중복확인 () throws Exception{
        // given
        // beforeEach()

        // when
        accountRepository.save(account1);
        accountRepository.save(account2);

        // then
        this.mockMvc.perform(post("/main").param("nickname","test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}