package com.ironhack.LeadService.controller.interfaces;

import com.ironhack.LeadService.model.Lead;

import java.util.List;

public interface LeadController {
    List<Lead> getAllLead();
    Lead getLeadById(Long id);
}
