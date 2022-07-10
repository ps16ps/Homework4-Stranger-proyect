package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dto.AccountDTO;
import com.ironhack.accountservice.controller.interfaces.AccountController;
import com.ironhack.accountservice.enums.Industry;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/employee-count/avg")
    @ResponseStatus(HttpStatus.OK)
    public double getAvgEmployeeCount() {
        return accountRepository.AvgEmployeeCount();
    }

    @GetMapping("/employee-count/med")
    @ResponseStatus(HttpStatus.OK)
    public double getMedEmployeeCount() {
        return  accountService.getMedEmployeeCount();
    }

    @GetMapping("/employee-count/max")
    @ResponseStatus(HttpStatus.OK)
    public int getMaxEmployeeCount() {
        return accountRepository.MaxEmployeeCount();
    }

    @GetMapping("/employee-count/min")
    @ResponseStatus(HttpStatus.OK)
    public int getMinEmployeeCount() {
        return accountRepository.MinEmployeeCount();
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        String industryString = accountDTO.getIndustry();
        int employeeCount = accountDTO.getEmployeeCount();
        String city = accountDTO.getCity();
        String country = accountDTO.getCountry();

        return accountService.createAccount(industryString,employeeCount,city,country);
    }
}
