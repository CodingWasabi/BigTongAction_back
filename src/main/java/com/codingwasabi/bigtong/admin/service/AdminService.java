package com.codingwasabi.bigtong.admin.service;

import com.codingwasabi.bigtong.User;
import com.codingwasabi.bigtong.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<User> userConnecting(){
        return userRepository.findAll();
    }
}
