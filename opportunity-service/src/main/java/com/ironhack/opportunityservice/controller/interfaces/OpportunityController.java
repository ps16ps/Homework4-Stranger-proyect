package com.ironhack.opportunityservice.controller.interfaces;


import com.ironhack.opportunityservice.controller.dto.OpportunityDTO;
import com.ironhack.opportunityservice.controller.dto.StatusDTO;
import com.ironhack.opportunityservice.model.Opportunity;

import java.util.List;
import java.util.Optional;

public interface OpportunityController {

    List<Opportunity> getAllOpportunities();

    Opportunity getOpportunityById(Long id);

    double getAvgQuantity();

    int getMaxQuantity();

    int getMinQuantity();

    double getMedQuantity();

    //Get by Status and Product
    List<Object[]> getAllOpportunitiesByProduct();

    List<Object[]> getOpportunitiesClosedWonByProduct();

    List<Object[]> getOpportunitiesClosedLostByProduct();

    List <Object[]> getOpportunitiesOpenByProduct();

    List<Object[]> getOpportunitiesBySalesRep();

    List<Object[]> getOpportunitiesClosedWonBySalesRep();

    List<Object[]> getOpportunitiesClosedLostBySalesRep();

    List<Object[]> getOpportunitiesOpenBySalesRep();

    double getAvgOppPerAccount();

    int getMaxOppPerAccount();

    int getMinOppPerAccount();

    double getMedOppPerAccount();

    Opportunity createOpportunity(OpportunityDTO opportunityDto);

    void updateStatus(Long id, StatusDTO statusDTO);
}
