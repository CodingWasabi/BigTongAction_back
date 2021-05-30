package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.common.userTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AdminControllerTest extends userTestFactory {

    @Test
    @DisplayName("ADMIN > 연결된유저 리스트")
    void ADMIN_유저리스트() throws Exception{

        // given
        // beforeEach()

        // when
        accountRepository.save(account1);
        accountRepository.save(account2);

        // then
        this.mockMvc.perform(get("/admin/connecting"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}