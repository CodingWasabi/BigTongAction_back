package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    // 바꿔야함
    @GetMapping("/connecting")
    public List<Account> showConnectiongUser(){
        return adminService.userConnecting();
    }

}
