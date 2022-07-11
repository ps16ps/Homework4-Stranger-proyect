package com.ironhack.opportunityservice.service.impl;

import com.ironhack.opportunityservice.enums.Product;
import com.ironhack.opportunityservice.enums.Status;
import com.ironhack.opportunityservice.model.Opportunity;
import com.ironhack.opportunityservice.repository.OpportunityRepository;
import com.ironhack.opportunityservice.service.interfaces.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;


@Service
public class OpportunityServiceImpl implements OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;


    public Opportunity getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity id " + id + " not found"));
        return opportunity;
    }

    public double getMedQuantity(){
        List<Integer> medList = opportunityRepository.MedQuantity_firstStep();
        int size = medList.size();
        int medIndex = size/2;
        double median = 0;
            if (size%2 != 0){
                median = medList.get(medIndex);
            } else {
                double median1 = medList.get(medIndex-1);
                double median2 = medList.get(medIndex);
                median = (median1+median2)/2;
        }
        return median;
    }

    public Opportunity createOpportunity(String product ,int quantity, Long decisionMakerId,
                                         String status, Long accountId, Long salesRepId ) {

        Product newProduct;
        Status newStatus;

        try {
            newStatus = Status.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Status not found. Status available are " + Arrays.toString(Status.values()));
        }

        try {
            newProduct = Product.valueOf(product.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Status not found. Status available are " + Arrays.toString(Status.values()));
        }

            Opportunity opportunity = new Opportunity(newProduct, quantity,
                    decisionMakerId, newStatus, accountId, salesRepId);

        return opportunityRepository.save(opportunity);
    }


    public void updateStatus(Long id, String status) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id " +  id + " not found."));
        Status newStatus;
        try {
            newStatus = Status.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Status not found. Status available are " + Arrays.toString(Status.values()));
        }

        opportunity.setStatus(newStatus);

        opportunityRepository.save(opportunity);
    }
}


