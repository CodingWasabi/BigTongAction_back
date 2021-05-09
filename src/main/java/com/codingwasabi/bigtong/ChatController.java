package com.codingwasabi.bigtong;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    @Transactional
    public ChatRoom createRoom(@RequestParam String name){
        log.info("room created : " + name);
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

    @GetMapping("/people")
    public int getPeople(@RequestParam Long roomId){
        ChatRoom chatRoom = chatService.findRoomById(roomId);

        return  chatRoom.getSessions().size();
    }
}
