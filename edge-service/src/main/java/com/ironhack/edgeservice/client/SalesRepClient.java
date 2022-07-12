package com.ironhack.edgeservice.client;


import com.ironhack.edgeservice.model.SalesRep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("sales-rep-service")
public interface SalesRepClient {

    @GetMapping("/sales-rep")
    List<SalesRep> getAllSalesRep();


    @GetMapping("/sales-rep/{id}")
    SalesRep getSalesRepById(@PathVariable Long id);


    @PostMapping("/sales-rep")
    SalesRep postSalesRep(@RequestBody SalesRep salesRep);




}
