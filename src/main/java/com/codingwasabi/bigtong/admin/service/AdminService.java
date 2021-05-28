package com.codingwasabi.bigtong.admin.service;

import com.codingwasabi.bigtong.Account;
import com.codingwasabi.bigtong.main.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public List<Account> userConnecting(){
        return accountRepository.findAll();
    }
}
