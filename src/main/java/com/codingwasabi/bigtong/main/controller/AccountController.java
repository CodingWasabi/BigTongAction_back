package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.main.dto.CurrentReturnDto;
import com.codingwasabi.bigtong.main.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
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

    @GetMapping("/current")
    public CurrentReturnDto returnCurrent(){
        return accountService.returnCurrent();
    }

    @DeleteMapping("/logout")
    public boolean logout(@RequestParam String name){
        return accountService.deleteNickname(name);
    }

}
