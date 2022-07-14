package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.StatusDTO;
import com.ironhack.edgeservice.model.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EdgeController {
    List<Lead> getAllLeads();

    List<SalesRep> getAllSalesRep();

    List<Contact> getAllContacts();

    List<Opportunity> getAllOpportunities();

    List<Account> getAllAccounts();
    Lead getLeadsById(Long id);

    SalesRep getSalesRepsById(Long id);

    Contact getContactsById(Long id);
    Lead postLead(Lead lead);
    SalesRep postSalesRep(SalesRep salesRep);

    Opportunity getOpportunitiesById( Long id);
    Account getAccountsById(Long id);
    void closeLostOpportunity(Long id);

    void closeWonOpportunity(Long id);
}
