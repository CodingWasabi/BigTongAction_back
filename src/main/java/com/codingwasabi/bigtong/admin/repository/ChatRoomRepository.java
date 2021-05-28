package com.codingwasabi.bigtong.admin.repository;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findChatRoomById(Long id);
}
