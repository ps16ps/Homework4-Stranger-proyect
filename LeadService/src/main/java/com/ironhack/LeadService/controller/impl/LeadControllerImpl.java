package com.ironhack.LeadService.controller.impl;

import com.ironhack.LeadService.controller.interfaces.LeadController;
import com.ironhack.LeadService.model.Lead;
import com.ironhack.LeadService.repository.LeadRepository;
import com.ironhack.LeadService.services.interfaces.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeadControllerImpl implements LeadController {

    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private LeadService leadService;

    @GetMapping("leads")
    @ResponseStatus(HttpStatus.OK)
    public List<Lead> getAllLead() {
        return leadRepository.findAll();
    }

    @GetMapping("/leads/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lead getLeadById(@PathVariable Long id) {
        return leadService.getLeadById(id);
    }
}
