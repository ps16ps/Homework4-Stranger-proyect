package com.ironhack.edgeservice.service.impl;

import com.ironhack.edgeservice.client.*;
import com.ironhack.edgeservice.service.interfaces.EdgeService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdgeServiceImpl implements EdgeService {

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private ContactClient contactClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private AccountClient accountClient;






}
