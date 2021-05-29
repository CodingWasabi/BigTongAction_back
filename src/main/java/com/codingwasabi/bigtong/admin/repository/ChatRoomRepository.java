package com.codingwasabi.bigtong.admin.repository;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findChatRoomByType(RoomType type);
}
