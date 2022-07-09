package com.ironhack.Homework3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "leads")

public class Lead {
    @Id  // indicate that id is the primary column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indicate that is auto increment column
    private long id; //long with l minus indicate that is not null column
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "sales_rep")
    private SalesRep salesRep;

//    protected static int leadIdCounter = 1;

    //constructors: void and with all the variables

    public Lead() {
    }

    public Lead(String name, String phoneNumber, String email, String companyName, SalesRep salesRep) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
        this.salesRep = salesRep;
    }

//    public Lead(String name, String phoneNumber, String email, String companyName) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.companyName = companyName;
//    }

    //getters and setters needed
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public SalesRep getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(SalesRep salesRep) {
        this.salesRep = salesRep;
    }

    //Strings to Print
    public String showLead(){
        return ("id: " + id + " -> name: " + name);
    }

    @Override
    public String toString() {
        return "=== Lead " + id + " ===\n"+
                "- name: " + name + "\n"+
                "- phone number: " + phoneNumber + "\n"+
                "- email: " + email + "\n"+
                "- company name: " + companyName + "\n"+
                "- salesRep id: " + salesRep.getId() +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return name.equals(lead.name) && phoneNumber.equals(lead.phoneNumber) && email.equals(lead.email) && Objects.equals(companyName, lead.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, companyName);
    }
}
