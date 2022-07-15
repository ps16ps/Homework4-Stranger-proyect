package com.ironhack.edgeservice.controller.interfaces;

import com.ironhack.edgeservice.controller.dto.ConvertDTO;
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
    Opportunity getOpportunitiesById( Long id);
    Account getAccountsById(Long id);
    double getAvgAccount();
    int getMaxAccount();
    int getMinAccount();
    double getMedAccount();
    double getAvgQuantity();
    int getMaxQuantity();
    int getMinQuantity();
    double getMedQuantity();
    List<Object[]> getAllOpportunitiesByProduct();
    List<Object[]> getOpportunitiesClosedWonByProduct();
    List<Object[]> getOpportunitiesClosedLostByProduct();
    List <Object[]> getOpportunitiesOpenByProduct();
    List <Object[]> getOpportunitiesBySalesRep();
    List <Object[]> getOpportunitiesClosedWonBySalesRep();
    List <Object[]> getOpportunitiesClosedLostBySalesRep();
    List <Object[]> getOpportunitiesOpenBySalesRep();
    List<Object[]> getOpportunitiesByCountry();
    List<Object[]> getClosedWonOpportunitiesByCountry();
    List<Object[]> getClosedLostOpportunitiesByCountry();
    List<Object[]> getOpenOpportunitiesByCountry();
    List<Object[]> getOpportunitiesByCity();
    List<Object[]> getClosedWonOpportunitiesByCity();
    List<Object[]> getClosedLostOpportunitiesByCity();
    List<Object[]> getOpenOpportunitiesByCity();
    List<Object[]> getOpportunitiesByIndustry();
    List<Object[]> getClosedWonOpportunitiesByIndustry();
    List<Object[]> getClosedLostOpportunitiesByIndustry();
    List<Object[]> getOpenOpportunitiesByIndustry();
    Lead postLead(Lead lead);
    SalesRep postSalesRep(SalesRep salesRep);
    String convertLead(Long id, ConvertDTO convertDTO);
    void closeLostOpportunity(Long id);
    void closeWonOpportunity(Long id);
}
