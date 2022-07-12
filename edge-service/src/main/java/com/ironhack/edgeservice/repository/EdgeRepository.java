package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EdgeRepository extends JpaRepository<Opportunity, Long> {

 /**Comenté las queries porque no me dejaba levantar, creo que solo le
  *  falta las relaciones en las columnas de los modelos
  *  para que funcione, que no se las coloqué.*/

/*
    @Query("SELECT o.salesRep, COUNT(o.id) FROM Opportunity o GROUP BY o.salesRep")
    List<Object[]> findOpportunitiesBySalesRep();

           //Count of all CLOSED_WON Opportunities by SalesRep
     @Query("SELECT o.salesRep, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_WON' GROUP BY o.salesRep")
     List<Object[]> findOpportunitiesClosedWonBySalesRep();

           //Count of all CLOSED_LOST Opportunities by SalesRep
     @Query("SELECT o.salesRep, COUNT(o.id) FROM Opportunity o WHERE o.status = 'CLOSED_LOST' GROUP BY o.salesRep")
     List<Object[]> findOpportunitiesClosedLostBySalesRep();

           //Count of all OPEN Opportunities by SalesRep
     @Query("SELECT o.salesRep, COUNT(o.id) FROM Opportunity o WHERE o.status = 'OPEN' GROUP BY o.salesRep")
     List<Object[]> findOpportunitiesOpenBySalesRep();

           //Count of all Opportunities by country
     @Query("SELECT a.country, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id GROUP BY a.country")
     List<Object[]> findOpportunitiesByCountry();

          //Count of all CLOSED_WON Opportunities by country
      @Query("SELECT a.country, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
          "WHERE o.status = 'CLOSED_WON' GROUP BY a.country")
     List<Object[]> findOpportunitiesClosedWonByCountry();

     //Count of all CLOSED_LOST Opportunities by country
     @Query("SELECT a.country, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
                  "WHERE o.status = 'CLOSED_LOST' GROUP BY a.country")
     List<Object[]> findOpportunitiesClosedLostByCountry();

     // Count of all OPEN Opportunities by country
     @Query("SELECT a.country, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
                  "WHERE o.status = 'OPEN' GROUP BY a.country")
     List<Object[]> findOpportunitiesOpenByCountry();

     //Count of all Opportunities by the city
     @Query("SELECT a.city, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id GROUP BY a.city")
     List<Object[]> findOpportunitiesByCity();

     //Count of all CLOSED_WON Opportunities by the city
     @Query("SELECT a.city, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
                 "WHERE o.status = 'CLOSED_WON' GROUP BY a.city")
     List<Object[]> findOpportunitiesClosedWonByCity();

     //Count of all CLOSED_LOST Opportunities by the city
     @Query("SELECT a.city, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
               "WHERE o.status = 'CLOSED_LOST' GROUP BY a.city")
     List<Object[]> findOpportunitiesClosedLostByCity();

     //Count of all OPEN Opportunities by the city
     @Query("SELECT a.city, COUNT(o.id) FROM Opportunity o JOIN Account a ON o.accountO=a.id " +
                "WHERE o.status = 'OPEN' GROUP BY a.city")
     List<Object[]> findOpportunitiesOpenByCity();

     //Count of all Opportunities by industry
     @Query("SELECT a.industry, COUNT(o.id) FROM Opportunity o JOIN Account a on o.accountO=a.id GROUP BY a.industry")
     List<Object[]> findOpportunitiesByIndustry();

     //Count of all CLOSED_WON Opportunities by industry
     @Query("SELECT a.industry, COUNT(o.id) FROM Opportunity o JOIN Account a on o.accountO=a.id " +
                  "WHERE o.status = 'CLOSED_WON' GROUP BY a.industry")
     List<Object[]> findOpportunitiesClosdeWonByIndustry();

     //Count of all CLOSED_LOST Opportunities by industry
     @Query("SELECT a.industry, COUNT(o.id) FROM Opportunity o JOIN Account a on o.accountO=a.id " +
                  "WHERE o.status = 'CLOSED_LOST' GROUP BY a.industry")
     List<Object[]> findOpportunitiesClosedLostByIndustry();

          //A count of all OPEN Opportunities by industry
     @Query("SELECT a.industry, COUNT(o.id) FROM Opportunity o JOIN Account a on o.accountO=a.id " +
                   "WHERE o.status = 'OPEN' GROUP BY a.industry")
     List<Object[]> findOpportunitiesOpenByIndustry();
*/




}
