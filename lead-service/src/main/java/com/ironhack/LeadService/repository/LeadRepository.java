package com.ironhack.LeadService.repository;

import com.ironhack.LeadService.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    //Count of Leads by SalesRepId
    @Query("SELECT l.salesRepId, COUNT(l.salesRepId) FROM Lead l GROUP BY l.salesRepId")
    List<Object[]> findLeadsBySalesRepId();
}