package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.Account;
import com.codingwasabi.bigtong.main.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @PostMapping("/in")
    public Long EnterSite(@RequestParam String name, HttpSession webSocketSession){

        // test log
        log.info("main : AccountController : EnterSite ");

        // 닉네임이 존재하지 않는 경우
        if(accountService.checkNickname(name)){
            Long id = accountService.createAccount(name);
            String sessionId = webSocketSession.getId();

            // account에 session이 잘 넘어갔다면
            if(accountService.insertSession(id,sessionId)){
                log.info("main : AccountController : EnterSite : session input well done");
                log.info("session info : " + webSocketSession.getId());
                return id;
            }
        }

        // 중복된 닉네임인 경우
        log.error("main : AccountController : EnterSite : session input error");
        return 0L;

    }

}
