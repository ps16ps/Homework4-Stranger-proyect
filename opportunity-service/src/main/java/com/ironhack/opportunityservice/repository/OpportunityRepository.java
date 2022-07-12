package com.ironhack.opportunityservice.repository;

import com.ironhack.opportunityservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {


    //The mean quantity of products order
    @Query("SELECT AVG(o.quantity) FROM Opportunity o")
    double AvgQuantity();

    // The median quantity of products order
    @Query("SELECT MAX(o.quantity) FROM Opportunity o")
    int MaxQuantity();

    //The minimum quantity of products order
    @Query("SELECT MIN(o.quantity) FROM Opportunity o")
    int MinQuantity();

    @Query("SELECT o.quantity FROM Opportunity o ORDER BY o.quantity")
    List<Integer> MedQuantity_firstStep();


    //By Product
    //Count of all Opportunities by the product
    @Query("SELECT o.product, COUNT(o.id) FROM Opportunity o GROUP BY o.product")
          List<Object[]> findOpportunitiesByProduct();

    //Count of all CLOSED_WON Opportunities by the product
    @Query("SELECT o.product, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_WON' GROUP BY o.product")
         List<Object[]> findOpportunitiesClosedWonByProduct();

    //Count of all CLOSED_LOST Opportunities by the product
    @Query("SELECT o.product, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_LOST' GROUP BY o.product")
          List<Object[]> findOpportunitiesClosedLostByProduct();

    //Count of all OPEN Opportunities by the product
    @Query("SELECT o.product, COUNT(o.id) FROM Opportunity o WHERE o.status = 'OPEN' GROUP BY o.product")
          List<Object[]> findOpportunitiesOpenByProduct();

    @Query("SELECT o.salesRepId, COUNT(o.id) FROM Opportunity o GROUP BY o.salesRepId")
    List<Object[]> findOpportunitiesBySalesRep();

    //Count of all CLOSED_WON Opportunities by SalesRep
    @Query("SELECT o.salesRepId, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_WON' GROUP BY o.salesRepId")
    List<Object[]> findOpportunitiesClosedWonBySalesRep();

    //Count of all CLOSED_LOST Opportunities by SalesRep
    @Query("SELECT o.salesRepId, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_LOST' GROUP BY o.salesRepId")
    List<Object[]> findOpportunitiesClosedLostBySalesRep();

    //Count of all OPEN Opportunities by SalesRep
    @Query("SELECT o.salesRepId, COUNT(o.id) FROM Opportunity o WHERE o.status = 'OPEN' GROUP BY o.salesRepId")
    List<Object[]> findOpportunitiesOpenBySalesRep();
}
