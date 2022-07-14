package com.ironhack.edgeservice.controller.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.dto.AccountDTO;
import com.ironhack.edgeservice.controller.dto.ConvertDTO;
import com.ironhack.edgeservice.controller.dto.OpportunityDTO;
import com.ironhack.edgeservice.controller.dto.StatusDTO;
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

    //All Opportunity Get Requests in opportunity client
    @GetMapping("/opportunity-quantity/avg")
    @ResponseStatus(HttpStatus.OK)
    public double getAvgQuantity(){
        return opportunityClient.getAvgQuantity();
    }
    @GetMapping("/opportunity-quantity/max")
    @ResponseStatus(HttpStatus.OK)
    public int getMaxQuantity(){
        return opportunityClient.getMaxQuantity();
    }

    @GetMapping("/opportunity-quantity/min")
    @ResponseStatus(HttpStatus.OK)
    public int getMinQuantity(){
        return opportunityClient.getMinQuantity();
    }

    @GetMapping("/opportunity-quantity/med")
    @ResponseStatus(HttpStatus.OK)
    public double getMedQuantity(){
        return opportunityClient.getMedQuantity();
    }

    @GetMapping("/opportunity-products")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAllOpportunitiesByProduct(){
        return opportunityClient.getAllOpportunitiesByProduct();
    }

    @GetMapping("/opportunity-products/closed-won")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getOpportunitiesClosedWonByProduct(){
        return opportunityClient.getOpportunitiesClosedWonByProduct();
    }

    @GetMapping("/opportunity-products/closed-lost")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getOpportunitiesClosedLostByProduct(){
        return opportunityClient.getOpportunitiesClosedLostByProduct();
    }
    @GetMapping("/opportunity-products/open")
    @ResponseStatus(HttpStatus.OK)
    public List <Object[]> getOpportunitiesOpenByProduct(){
        return opportunityClient.getOpportunitiesOpenByProduct();
    }

    @GetMapping("/opportunity-sales-rep")
    @ResponseStatus(HttpStatus.OK)
    public List <Object[]> getOpportunitiesBySalesRep(){
        return opportunityClient.getOpportunitiesBySalesRep();
    };
    @GetMapping("/opportunity-sales-rep/closed-won")
    public List <Object[]> getOpportunitiesClosedWonBySalesRep(){
        return opportunityClient.getOpportunitiesClosedWonBySalesRep();
    }

    @GetMapping("/opportunity-sales-rep/closed-lost")
    public List <Object[]> getOpportunitiesClosedLostBySalesRep(){
        return opportunityClient.getOpportunitiesClosedLostBySalesRep();
    }

    @GetMapping("/opportunity-sales-rep/open")
    public List <Object[]> getOpportunitiesOpenBySalesRep(){
        return opportunityClient.getOpportunitiesOpenBySalesRep();
    }

    // GET QUERYs : agregar las del EdgeRepo

    @GetMapping("/opportunities/country")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> findOpportunitiesByCountry(){
        return edgeRepository.findOpportunitiesByCountry();
    }

    //Posts : new salesRep, new lead, convert lead
    @PostMapping("/leads")
    @ResponseStatus(HttpStatus.CREATED)
    public Lead postLead(@RequestBody Lead lead){
        return leadClient.postLead(lead);
    }

    @PostMapping("/sales-rep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRep postSalesRep(@RequestBody SalesRep salesRep){
        return salesRepClient.postSalesRep(salesRep);
    }
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
                contact.getId(),account.getId(), lead.getSalesRepId());
        Opportunity opportunity = opportunityClient.createOpportunity(opportunityDTO);
        //TODO: DELETE LEAD
        return "Lead " + id + " converted";
    }

    //Put??

    //Patch
    @PatchMapping("/opportunities/{id}/update-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Long id, @RequestBody StatusDTO statusDTO){
        opportunityClient.updateStatus(id,statusDTO);
    }

    @PatchMapping("/opportunities/{id}/close-lost")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeLostOpportunity(@PathVariable Long id){
        StatusDTO statusDTO = new StatusDTO("CLOSED_LOST");
        opportunityClient.updateStatus(id,statusDTO);
    }
//
    @PatchMapping("/opportunities/{id}/close-won")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeWonOpportunity(@PathVariable Long id){
        StatusDTO statusDTO = new StatusDTO("CLOSED_WON");
        opportunityClient.updateStatus(id,statusDTO);
    }
    //Delete



}
