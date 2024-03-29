package com.ironhack.SalesRep.controller.impl;

import com.ironhack.SalesRep.controller.interfaces.SalesRepController;
import com.ironhack.SalesRep.model.SalesRep;
import com.ironhack.SalesRep.repository.SalesRepRepository;
import com.ironhack.SalesRep.services.interfaces.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SalesRepControllerImpl implements SalesRepController {

    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private SalesRepService salesRepService;

    @GetMapping("/sales-rep")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRep> getAllSalesRep() {
        return salesRepRepository.findAll();
    }

    @GetMapping("/sales-rep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRep getSalesRepById(@PathVariable Long id) {
        return salesRepService.getSalesRepById(id);
    }

    @PostMapping("/sales-rep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRep postSalesRep(@RequestBody SalesRep salesRep){
        return salesRepRepository.save(salesRep);
    }
}
