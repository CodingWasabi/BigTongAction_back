package com.codingwasabi.bigtong.main.service;

import com.codingwasabi.bigtong.User;
import com.codingwasabi.bigtong.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;

    public boolean checkNickname(String nickname){
        User user= userRepository.findByNickname("name").orElse(null);
        if(user == null){
            user = User.builder()
                    .nickname(nickname)
                    .build();
            userRepository.save(user);

            return true;
        }

        else
            return false;
    }
}
