package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.model.Account;

public interface AccountService {
    Account getAccountById(Long id);
    Account createAccount(String industryString,int employeeCount,String city,String country);
}
