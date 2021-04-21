package com.codingwasabi.bigtong;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")

public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name){
        return chatService.createChatRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }

    @GetMapping("/topId")
    public List<Long> getTopId(){
        List<Long> list = new ArrayList<>();
        List<ChatRoom> chatroomList =  chatService.findAllRoom();
        for(ChatRoom chatRoom : chatroomList){
            list.add(chatRoom.getRoomId());
        }
        return list;
    }
}
