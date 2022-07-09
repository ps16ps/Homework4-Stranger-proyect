package com.ironhack.Homework3.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sales_rep_table")
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "salesRep")
    private List<Lead> leadList;
    @OneToMany(mappedBy = "salesRep")
    private List<Opportunity> opportunityList;

    public SalesRep() {
    }

    public SalesRep(String name) {
        this.name = name;
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Strings to Print
    public String showSalesRep(){
        return ("id: " + id + " -> name: " + name);
    }

    public List<Lead> getLeadList() {
        return leadList;
    }

    public void setLeadList(List<Lead> leadList) {
        this.leadList = leadList;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesRep salesRep = (SalesRep) o;
        return name.equals(salesRep.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
