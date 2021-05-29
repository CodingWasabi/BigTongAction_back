package com.codingwasabi.bigtong.main.service;

import com.codingwasabi.bigtong.main.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<Account, Session> account_session = new HashMap<>();

    @Autowired
    AccountRepository accountRepository;

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

}
