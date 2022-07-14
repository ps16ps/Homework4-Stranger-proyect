package com.ironhack.LeadService.services.impl;

import com.ironhack.LeadService.model.Lead;
import com.ironhack.LeadService.repository.LeadRepository;
import com.ironhack.LeadService.services.interfaces.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepository leadRepository;


    @Override
    public Lead getLeadById(Long id) {
        Lead lead = leadRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead not found"));
        return lead;
    }

    @Override
    public void delete(Long id) {
        Lead lead = leadRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead not found"));
        leadRepository.delete(lead);
    }
}
