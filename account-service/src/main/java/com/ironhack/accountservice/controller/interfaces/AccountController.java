package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dto.AccountDTO;
import com.ironhack.accountservice.model.Account;

import java.util.List;

public interface AccountController {
    List<Account> getAllAccount();
    Account getAccountById(Long id);
    Account createAccount(AccountDTO accountDTO);
}
