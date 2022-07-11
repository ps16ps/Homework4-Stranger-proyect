package com.ironhack.SalesRep.services.impl;

import com.ironhack.SalesRep.controller.impl.SalesRepControllerImpl;
import com.ironhack.SalesRep.model.SalesRep;
import com.ironhack.SalesRep.repository.SalesRepRepository;
import com.ironhack.SalesRep.services.interfaces.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SalesRepServiceImpl implements SalesRepControllerImpl {

    @Autowired
    private SalesRepRepository salesRepRepository;


    @Override
    public SalesRep getSalesRepById(Long id) {
        SalesRep salesRep = salesRepRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead not found"));
        return salesRep;
    }
}
