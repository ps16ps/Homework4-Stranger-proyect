package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.controller.dto.AccountDTO;
import com.ironhack.edgeservice.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/accounts")
    List<Account> getAllAccount();

    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable Long id);

    @GetMapping("/employee-count/avg")
    public double getAvgEmployeeCount();

    @GetMapping("/employee-count/med")
    public double getMedEmployeeCount();

    @GetMapping("/employee-count/max")
    public int getMaxEmployeeCount();

    @GetMapping("/employee-count/min")
    int getMinEmployeeCount();

    @PostMapping("/accounts")
    Account createAccount(@RequestBody @Valid AccountDTO accountDTO);


}


