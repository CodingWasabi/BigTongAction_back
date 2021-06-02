package com.codingwasabi.bigtong.admin.service;


import com.codingwasabi.bigtong.admin.entity.RoomType;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import com.codingwasabi.bigtong.websocket.exception.ChatRoomNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ChatRoomService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createChatRoom(String type){

        // test log
        log.info("admin : chatRoomService : createChatRoom "+type);

        ChatRoom chatRoom = ChatRoom.builder()
                .type(RoomType.valueOf(type))
                .build();


        chatRoomRepository.save(chatRoom);

        return chatRoom.getId();
    }


    public boolean checkRoomExist(){
        chatRoomRepository.findChatRoomByType(RoomType.GRAIN).orElseThrow(ChatRoomNotExistException::new);
        chatRoomRepository.findChatRoomByType(RoomType.FISH).orElseThrow(ChatRoomNotExistException::new);
        chatRoomRepository.findChatRoomByType(RoomType.FRUIT).orElseThrow(ChatRoomNotExistException::new);
        chatRoomRepository.findChatRoomByType(RoomType.VEGETABLE).orElseThrow(ChatRoomNotExistException::new);
        chatRoomRepository.findChatRoomByType(RoomType.MEAT).orElseThrow(ChatRoomNotExistException::new);

        return true;
    }
}
