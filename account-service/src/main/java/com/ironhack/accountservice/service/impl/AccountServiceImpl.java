package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.enums.Industry;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id " + id + "not found"));
        return account;
    }

    public Account createAccount(String industryString, int employeeCount, String city, String country) {
        Industry industryEnum;
        try{
            industryEnum = Industry.valueOf(industryString.toUpperCase());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The industry has to be one of the following " +
                    "ones: PRODUCE, ECOMMERCE, MANUFACTURING, MEDICAL, OTHER");
        }
        Account account = new Account(industryEnum,employeeCount,city,country);

        //CHECK: Se pueden tener dos cuentas distintas con los mismos datos???

//        List<Account> accounts = accountRepository.findAll();
//        for (Account account1:accounts){
//            if (account1.equals(account)){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account already exists");
//            }
//        }
        return accountRepository.save(account);
    }
}
