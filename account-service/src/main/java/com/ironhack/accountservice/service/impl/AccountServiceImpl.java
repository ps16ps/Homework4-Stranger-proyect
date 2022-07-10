package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.enums.Industry;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id " + id + " not found"));
        return account;
    }

    public double getMedEmployeeCount() {
        List<Integer> medianList = accountRepository.MedEmployeeCount_firstStep();
        int size = medianList.size();
        int medIndex = size/2;
        double median = medianList.get(medIndex);
        if (size%2 == 0){
            double median1 = medianList.get(medIndex-1);
            double median2 = medianList.get(medIndex);
            median = (median1+median2)/2;
        }
        return median;
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
