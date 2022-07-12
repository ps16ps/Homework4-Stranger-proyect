package com.ironhack.edgeservice.controller.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.interfaces.EdgeController;
import com.ironhack.edgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EdgeControllerImpl implements EdgeController {

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private ContactClient contactClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private AccountClient accountClient;

    //Gets
    @GetMapping("/leads")
    @ResponseStatus(HttpStatus.OK)
    public List<Lead> getAllLeads(){
        return leadClient.getAllLead();
    }

    @GetMapping("/sales-rep")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRep> getAllSalesRep() {
        return salesRepClient.getAllSalesRep();
    }

     @GetMapping("/contacts")
     @ResponseStatus(HttpStatus.OK)
     public List<Contact> getAllContacts() {
         return contactClient.findAll();
     }

    @GetMapping("/opportunities")
    @ResponseStatus(HttpStatus.OK)
    public List<Opportunity> getAllOpportunities(){
        return opportunityClient.getAllOpportunities();
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts(){
        return accountClient.getAllAccount();
    }

    //Posts


    //Put??

    //Patch


    //Delete



}
