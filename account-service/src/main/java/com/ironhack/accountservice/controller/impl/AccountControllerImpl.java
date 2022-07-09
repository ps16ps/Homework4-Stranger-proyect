package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.interfaces.AccountController;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id " + id + "not found"));
        return account;
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(Account account) {
        List<Account> accounts = accountRepository.findAll();
        for (Account account1:accounts){
            if (account1.equals(account)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account already exists");
            }
        }
        return accountRepository.save(account);
    }
}
