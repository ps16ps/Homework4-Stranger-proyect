package com.ironhack.SalesRep.repository;

import com.ironhack.SalesRep.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, Long> {
    @Query("SELECT l.salesRepId, COUNT(l.salesRepId) FROM Lead l GROUP BY l.salesRepId")
    List<Object[]> findSalesRepsById();
}