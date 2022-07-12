package com.ironhack.edgeservice.controller.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.dto.AccountDTO;
import com.ironhack.edgeservice.controller.dto.ConvertDTO;
import com.ironhack.edgeservice.controller.dto.OpportunityDTO;
import com.ironhack.edgeservice.controller.interfaces.EdgeController;
import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.model.*;
import com.ironhack.edgeservice.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EdgeRepository edgeRepository;

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

    // Get QUERYs

    @GetMapping("/opportunities/country")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> findOpportunitiesByCountry(){
        return edgeRepository.findOpportunitiesByCountry();
    }

    //Posts
    // new salesRep, new lead, convert lead
    @PostMapping("/convert/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String convertLead(@PathVariable Long id, @RequestBody ConvertDTO convertDTO){
        Lead lead = leadClient.getLeadById(id);
        Account account;
        if (convertDTO.getAccountId() != null){
            account = accountClient.getAccountById(convertDTO.getAccountId());
        } else {
            AccountDTO accountDTO = new AccountDTO(convertDTO.getIndustry(),convertDTO.getEmployeeCount(),
                    convertDTO.getCity(),convertDTO.getCountry());
            account = accountClient.createAccount(accountDTO);
        }
        Contact contact = new Contact(lead.getName(),lead.getPhoneNumber(),lead.getEmail(),lead.getCompanyName(),
                account.getId());
        contact = contactClient.saveContact(contact);
        OpportunityDTO opportunityDTO = new OpportunityDTO(convertDTO.getProduct(), convertDTO.getQuantity(),
                "OPEN", contact.getId(),account.getId(), lead.getSalesRepId());
        Opportunity opportunity = opportunityClient.createOpportunity(opportunityDTO);
        //TODO: DELETE LEAD
        return "Lead " + id + " converted";
    }

    //Put??

    //Patch


    //Delete



}
