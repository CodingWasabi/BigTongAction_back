package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.common.TestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class APIControllerTest extends TestFactory {

    @Test
    void callGrainApi() throws Exception{
        // given
        // when

        // then
        doPerform("/object/grain");
    }

    @Test
    void callFruitApi() throws Exception{
        // given
        // when

        // then
        doPerform("/object/fruit");
    }

    @Test
    void callVegetableApi() throws Exception{
        // given
        // when

        // then
        doPerform("/object/vegetable");
    }

    @Test
    void callMeatApi() throws Exception{
        // given
        // when

        // then
        doPerform("/object/meat");
    }

    @Test
    void callFishApi() throws Exception{
        // given
        // when

        // then
        doPerform("/object/fish");
    }

    private void doPerform(String addr) throws Exception{
        this.mockMvc.perform(get(addr))
                .andExpect(status().isOk())
                .andDo(print());
    }
}