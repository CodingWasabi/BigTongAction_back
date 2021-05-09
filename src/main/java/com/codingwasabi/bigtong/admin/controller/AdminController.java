package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.User;
import com.codingwasabi.bigtong.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/connecting")
    public List<User> showConnectiongUser(){
        return adminService.userConnecting();
    }
}
