package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.User;
import com.codingwasabi.bigtong.common.userTestFactory;
import com.codingwasabi.bigtong.main.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
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
        userRepository.save(user1);
        userRepository.save(user2);

        // then
        this.mockMvc.perform(get("/admin/connecting"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}