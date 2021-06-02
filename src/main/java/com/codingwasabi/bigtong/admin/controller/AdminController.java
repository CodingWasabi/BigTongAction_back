package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.service.ChatRoomService;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final ChatRoomService chatRoomService;


    // 바꿔야함
    @GetMapping("/connecting")
    public String showConnectiongUser(Model model){

        List<Account> accountList = adminService.userConnecting();

        model.addAttribute("accountList",accountList);
        model.addAttribute("count",accountList.size());
        model.addAttribute("isNull","접속된 유저가 현재 없습니다.");

        return "connecting";
    }


    @PostMapping("/create")
    public String create_chatRoom(){

        if(!chatRoomService.checkRoomExist()){
            chatRoomService.createChatRoom("GRAIN");
            chatRoomService.createChatRoom("VEGETABLE");
            chatRoomService.createChatRoom("FRUIT");
            chatRoomService.createChatRoom("MEAT");
            chatRoomService.createChatRoom("FISH");
        }
        return "redirect:/";
    }

}
