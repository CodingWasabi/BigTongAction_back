package com.codingwasabi.bigtong.admin.controller;

import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public Long create_chatRoom(@RequestParam String type){

        // test log
        log.info("admin : ChatRoomController : create_chatRoom");
        return chatRoomService.createChatRoom(type);
    }
}
