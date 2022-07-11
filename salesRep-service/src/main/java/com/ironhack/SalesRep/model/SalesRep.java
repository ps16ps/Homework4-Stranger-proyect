package com.ironhack.SalesRep.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SalesRep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;
    @JoinColumn(name="sales_rep")
    private Long salesRepId;

    public SalesRep() {
    }

    public SalesRep(String name, String phoneNumber, String email, String companyName,Long salesRepId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
        this.salesRepId = salesRepId;
    }


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

    public Long getSalesRep() {
        return salesRepId;
    }

    public void setSalesRepId(Long salesRep) {
        this.salesRepId = salesRepId;
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
                "- salesRep id: " + salesRepId +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesRep salesRep = (SalesRep) o;
        return name.equals(salesRep.name) && phoneNumber.equals(salesRep.phoneNumber) && email.equals(salesRep.email) && Objects.equals(companyName, salesRep.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, companyName);
    }
}
