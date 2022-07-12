package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.model.Lead;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("lead-service")
public interface LeadClient {

    @GetMapping("/leads")
    public List<Lead> getAllLead();


    @GetMapping("/leads/{id}")
    public Lead getLeadById(@PathVariable Long id);

    @PostMapping("/leads")
    public Lead postLead(@RequestBody Lead lead);



}
