package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.main.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    /**
     * 닉네임을 입력한 후 , 메인 홈으로 이동
     * @param name
     * @return
     */
    @PostMapping("/enter")
    public String EnterSite(@RequestParam String name){

        // 닉네임 중복확인
        if(accountService.checkNickname(name)){
            accountService.createAccount(name);
            return name;
        }

        return "error/failed";
    }

}
