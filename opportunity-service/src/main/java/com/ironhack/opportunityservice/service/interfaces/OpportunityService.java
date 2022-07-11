package com.ironhack.opportunityservice.service.interfaces;

import com.ironhack.opportunityservice.model.Opportunity;

import java.util.List;


public interface OpportunityService {

    Opportunity getOpportunityById(Long id);

    double getMedQuantity();
    Opportunity createOpportunity(String product ,int quantity, Long decisionMakerId,
                                  String status, Long accountId, Long salesRepId );

    void updateStatus(Long id, String status);

}
