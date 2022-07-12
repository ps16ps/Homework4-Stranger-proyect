package com.ironhack.edgeservice.controller.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.interfaces.EdgeController;
import com.ironhack.edgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/leads/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lead getLeadsById(@PathVariable Long id){
        return leadClient.getLeadById(id);
    }

    @GetMapping("/sales-rep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRep getSalesRepsById(@PathVariable Long id) {
        return salesRepClient.getSalesRepById(id);
    }

    @GetMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contact getContactsById(@PathVariable Long id) {
        return contactClient.findById(id);
    }

    @GetMapping("/opportunities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Opportunity getOpportunitiesById(@PathVariable Long id){
        return opportunityClient.getOpportunityById(id);
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountsById(@PathVariable Long id){
        return accountClient.getAccountById(id);
    }

    //Posts


    //Put??

    //Patch


    //Delete



}
