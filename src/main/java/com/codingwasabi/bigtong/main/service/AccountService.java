package com.codingwasabi.bigtong.main.service;

import com.codingwasabi.bigtong.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public boolean checkNickname(String nickname){
        Account account = accountRepository.findByNickname("name").orElse(null);
        if(account == null)
            return true;
        return false;
    }

    @Transactional
    public Long createAccount(String nickname){
        Account account = Account.builder()
                .nickname(nickname)
                .build();
        accountRepository.save(account);

        return account.getId();
    }

    @Transactional
    public boolean insertSession(Long id,String sesssionId){
        Account account = accountRepository.findAccountById(id);
        account.insertSession(sesssionId);

        if(account.getSessionId() == null)
            return false;

        return true;
    }
}
