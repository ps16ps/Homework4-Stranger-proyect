package com.ironhack.edgeservice.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //long with l minus indicate that is not null column
    @Pattern(regexp = "^[A-Za-z ]*$", message = "The name can not contain numbers")
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;

    private Long salesRepId;

    public Lead() {
    }

    public Lead(String name, String phoneNumber, String email, String companyName, Long salesRepId) {
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

    public Long getSalesRepId() {
        return salesRepId;
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
        Lead lead = (Lead) o;
        return name.equals(lead.name) && phoneNumber.equals(lead.phoneNumber) && email.equals(lead.email) &&
                Objects.equals(companyName, lead.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, companyName);
    }
}