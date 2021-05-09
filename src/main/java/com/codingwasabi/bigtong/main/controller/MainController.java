package com.codingwasabi.bigtong.main.controller;

import com.codingwasabi.bigtong.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @PostMapping
    public boolean checkNickname(@RequestParam String nickname){
        return mainService.checkNickname(nickname);
    }



}
