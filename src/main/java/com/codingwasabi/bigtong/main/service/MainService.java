package com.codingwasabi.bigtong.main.service;

import com.codingwasabi.bigtong.Account;
import com.codingwasabi.bigtong.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;

    public boolean checkNickname(String nickname){
        Account account = userRepository.findByNickname("name").orElse(null);
        if(account == null){
            account = Account.builder()
                    .nickname(nickname)
                    .build();
            userRepository.save(account);

            return true;
        }

        else
            return false;
    }
}
