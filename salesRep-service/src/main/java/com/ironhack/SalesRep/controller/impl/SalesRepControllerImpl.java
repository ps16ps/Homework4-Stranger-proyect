package com.ironhack.SalesRep.controller.impl;

import com.ironhack.SalesRep.controller.interfaces.SalesRepController;
import com.ironhack.SalesRep.model.SalesRep;
import com.ironhack.SalesRep.repository.SalesRepRepository;
import com.ironhack.SalesRep.services.interfaces.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SalesRepControllerImpl implements SalesRepController {

    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private SalesRepService salesRepService;

    @GetMapping("salesRep")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRep> getAllSalesRep() {
        return salesRepRepository.findAll();
    }

    @GetMapping("/salesRep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRep getSalesRepById(@PathVariable Long id) {
        return salesRepService.getSalesRepById(id);
    }

//    @GetMapping("/salesRep/status/{status}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<SalesRep> getSalesRepByStatus(@PathVariable String status) {
//        return salesRepService.getSalesRepByStatus(status);
//    }

}
