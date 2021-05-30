package com.codingwasabi.bigtong.main.service;

import com.codingwasabi.bigtong.admin.entity.ChatRoom;
import com.codingwasabi.bigtong.admin.repository.ChatRoomRepository;
import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import com.codingwasabi.bigtong.websocket.message.UpdateMessage;
import com.codingwasabi.bigtong.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    /**
     * 닉네임 중복확인
     * @param nickname
     * @return
     */
    public boolean checkNickname(String nickname){

        // Account 테이블에 입력된 nickname이 있는지 확인, 없다면 null 입력
        Account account = accountRepository.findByNickname(nickname).orElse(null);

        // 없는 경우, 진행
        if(account == null)
            return true;

        return false;
    }


    /**
     * 닉네임 생성  (DB에 입력)
     * @param nickname
     * @return
     */
    @Transactional
    public Long createAccount(String nickname){
        Account account = Account.builder()
                .nickname(nickname)
                .build();
        accountRepository.save(account);

        return account.getId();
    }


    @Transactional
    public void deleteAccount(String nickname,Map<String,WebSocketSession> webSocketSessionMap){


        Account account = accountRepository.findAccountByNickname(nickname);

        // 해당 세션의 account 가 현재 속한 방이 있는지 여부 확인
        // 속한 방이 있다면
        if(account.getChatRoom() != null) {
            ChatRoom chatRoom = account.getChatRoom();

            accountRepository.deleteAccountByNickname(nickname);
            chatRoomRepository.flush();

            chatService.returnToAll(webSocketSessionMap,chatRoom);

        }

        // 없다면
        else
            accountRepository.deleteAccountByNickname(nickname);
    }
}
