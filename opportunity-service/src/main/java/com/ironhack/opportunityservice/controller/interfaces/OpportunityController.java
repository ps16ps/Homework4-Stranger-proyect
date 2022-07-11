package com.ironhack.opportunityservice.controller.interfaces;


import com.ironhack.opportunityservice.controller.dto.OpportunityDTO;
import com.ironhack.opportunityservice.controller.dto.StatusDTO;
import com.ironhack.opportunityservice.model.Opportunity;

import java.util.List;

public interface OpportunityController {

    List<Opportunity> getAllOpportunities();

    Opportunity getOpportunityById(Long id);

    double getAvgQuantity();

    int getMaxQuantity();

    int getMinQuantity();

    double getMedQuantity();

    Opportunity createOpportunity(OpportunityDTO opportunityDto);

    void updateStatus(Long id, StatusDTO statusDTO);



}
