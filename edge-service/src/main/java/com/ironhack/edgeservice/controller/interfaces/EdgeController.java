package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.model.*;

import java.util.List;

public interface EdgeController {


    List<Lead> getAllLeads();

    List<SalesRep> getAllSalesRep();

    List<Contact> getAllContacts();

    List<Opportunity> getAllOpportunities();

    List<Account> getAllAccounts();
}
