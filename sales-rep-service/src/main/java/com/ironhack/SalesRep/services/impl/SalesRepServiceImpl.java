package com.ironhack.SalesRep.services.impl;

import com.ironhack.SalesRep.model.SalesRep;
import com.ironhack.SalesRep.repository.SalesRepRepository;
import com.ironhack.SalesRep.services.interfaces.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SalesRepServiceImpl implements SalesRepService {

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Override
    public SalesRep getSalesRepById(Long id) {
        SalesRep salesRep = salesRepRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "SalesRep not found"));
        return salesRep;
    }
}
