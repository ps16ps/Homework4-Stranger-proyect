package com.ironhack.opportunityservice.controller.impl;

import com.ironhack.opportunityservice.controller.dto.OpportunityDTO;
import com.ironhack.opportunityservice.controller.dto.StatusDTO;
import com.ironhack.opportunityservice.controller.interfaces.OpportunityController;
import com.ironhack.opportunityservice.model.Opportunity;
import com.ironhack.opportunityservice.repository.OpportunityRepository;
import com.ironhack.opportunityservice.service.interfaces.OpportunityService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;
@RestController
public class OpportunityControllerImpl implements OpportunityController {


    @Autowired
    OpportunityService opportunityService;
    @Autowired
    OpportunityRepository opportunityRepository;

    @GetMapping("/opportunities")
    @ResponseStatus(HttpStatus.OK)
    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    @GetMapping("/opportunities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Opportunity getOpportunityById(@PathVariable Long id) {

        return opportunityService.getOpportunityById(id);
    }
        //Pueden ser request params? en un solo get?
    @GetMapping("/opportunity-quantity/avg")
    @ResponseStatus(HttpStatus.OK)
    public double getAvgQuantity() {
        return opportunityRepository.AvgQuantity();
    }

    @GetMapping("/opportunity-quantity/max")
    @ResponseStatus(HttpStatus.OK)
    public int getMaxQuantity() {
        return opportunityRepository.MaxQuantity();
    }

    @GetMapping("/opportunity-quantity/min")
    @ResponseStatus(HttpStatus.OK)
    public int getMinQuantity() {
        return opportunityRepository.MinQuantity();
    }

    @GetMapping("/opportunity-quantity/med")
    @ResponseStatus(HttpStatus.OK)
    public double getMedQuantity(){
      return opportunityService.getMedQuantity();
    };

    @PostMapping("/opportunities")
    @ResponseStatus(HttpStatus.CREATED)
    public Opportunity createOpportunity(@RequestBody OpportunityDTO opportunityDto) {
        String product = opportunityDto.getProduct();
        int quantity = opportunityDto.getQuantity();
        Long decisionMakerId = opportunityDto.getDecisionMakerId();
        String status = opportunityDto.getStatus();
        Long accountId = opportunityDto.getAccountId();
        Long salesRepId = opportunityDto.getSalesRepId();

       return opportunityService.createOpportunity(product, quantity,
               decisionMakerId, status, accountId, salesRepId);
    }

    @PatchMapping("/opportunities/{id}/update-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Long id, @RequestBody StatusDTO statusDTO){
    }

}
