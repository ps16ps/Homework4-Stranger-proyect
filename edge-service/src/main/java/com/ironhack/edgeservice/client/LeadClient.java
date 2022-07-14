package com.ironhack.edgeservice.client;

import com.ironhack.edgeservice.model.Lead;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("lead-service")
public interface LeadClient {

    @GetMapping("/leads")
    List<Lead> getAllLead();

    @GetMapping("/leads/{id}")
    Lead getLeadById(@PathVariable Long id);

    @PostMapping("/leads")
    Lead postLead(@RequestBody Lead lead);

    @DeleteMapping("/leads/{id}")
    void delete(@PathVariable Long id);

}
