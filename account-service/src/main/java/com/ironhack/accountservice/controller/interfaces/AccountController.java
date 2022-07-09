package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.model.Account;

import java.util.List;

public interface AccountController {
    List<Account> getAllAccount();
    Account getAccountById(Long id);
    Account createAccount(Account account);
}
