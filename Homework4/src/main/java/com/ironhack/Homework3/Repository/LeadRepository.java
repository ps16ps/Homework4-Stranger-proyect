package com.ironhack.Homework3.Repository;

import com.ironhack.Homework3.model.Lead;
import com.ironhack.Homework3.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    //Count of Leads by SalesRep
    @Query("SELECT l.salesRep, COUNT(l.salesRep) FROM Lead l GROUP BY l.salesRep")
    List<Object[]> findLeadsBySalesRep();
}

