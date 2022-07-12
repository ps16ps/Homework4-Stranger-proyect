package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.controller.dto.OpportunityDTO;
import com.ironhack.edgeservice.controller.dto.StatusDTO;
import com.ironhack.edgeservice.model.Opportunity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("opportunity-service")
public interface OpportunityClient {

    @GetMapping("/opportunities")
    List<Opportunity> getAllOpportunities();

    @GetMapping("/opportunities/{id}")
    Opportunity getOpportunityById(@PathVariable Long id);

    //Pueden ser request params? en un solo get?
    @GetMapping("/opportunity-quantity/avg")
    double getAvgQuantity();

    @GetMapping("/opportunity-quantity/max")
    int getMaxQuantity();

    @GetMapping("/opportunity-quantity/min")
    public int getMinQuantity();

    @GetMapping("/opportunity-quantity/med")
    double getMedQuantity();

    @GetMapping("/opportunity-products")
    List<Object[]> getAllOpportunitiesByProduct();

    @GetMapping("/opportunity-products/closed-won")
    List<Object[]> getOpportunitiesClosedWonByProduct();

    @GetMapping("/opportunity-products/closed-lost")
    List<Object[]> getOpportunitiesClosedLostByProduct();


    @GetMapping("/opportunity-products/open")
    List <Object[]> getOpportunitiesOpenByProduct();

    @PostMapping("/opportunities")
    Opportunity createOpportunity(@RequestBody OpportunityDTO opportunityDto);

    @PatchMapping("/opportunities/{id}/update-status")
    void updateStatus(@PathVariable Long id, @RequestBody StatusDTO statusDTO);




}