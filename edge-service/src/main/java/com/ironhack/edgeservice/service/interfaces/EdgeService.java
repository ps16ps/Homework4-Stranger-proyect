package com.ironhack.edgeservice.service.interfaces;

import com.ironhack.edgeservice.controller.dto.ConvertDTO;

public interface EdgeService {
    String convertLead(Long id, ConvertDTO convertDTO);
}
