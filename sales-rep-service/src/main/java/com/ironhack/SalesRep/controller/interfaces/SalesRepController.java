package com.ironhack.SalesRep.controller.interfaces;

import com.ironhack.SalesRep.model.SalesRep;

import java.util.List;

public interface SalesRepController {
    List<SalesRep> getAllSalesRep();
    SalesRep getSalesRepById(Long id);
    SalesRep postSalesRep(SalesRep salesRep);
}