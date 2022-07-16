package com.ironhack.edgeservice.service.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.controller.dto.AccountDTO;
import com.ironhack.edgeservice.controller.dto.ConvertDTO;
import com.ironhack.edgeservice.controller.dto.OpportunityDTO;
import com.ironhack.edgeservice.model.Account;
import com.ironhack.edgeservice.model.Contact;
import com.ironhack.edgeservice.model.Lead;
import com.ironhack.edgeservice.model.Opportunity;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EdgeServiceImpl implements EdgeService {

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

    @CircuitBreaker(name = "convertLead", fallbackMethod = "convertLeadFallback")
    public String convertLead(Long id, ConvertDTO convertDTO){
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
        leadClient.delete(id);
        return "Lead " + id + " converted";
    }

    public String convertLeadFallback(Long id, ConvertDTO convertDTO, Exception e){
        throw new RuntimeException(e.getMessage());
//        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The server is not working. Try later");
    }
}
