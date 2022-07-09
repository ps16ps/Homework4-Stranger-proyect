package com.ironhack.Homework3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Contact{

//    private static int contactIdCounter = 100000;
    @Id  // indicate that id is the primary column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indicate that is auto increment column
    private long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String companyName;
    //relation Many TO One
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account accountC;
    @OneToOne(mappedBy = "decisionMaker")
    private Opportunity opportunity;
    //constructors: void and with all the variables
    public Contact() {
    }

    public Contact(String name, String phoneNumber, String email, String companyName, Account accountC) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
        this.accountC = accountC;
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

    public Account getAccountC() {
        return accountC;
    }

    public void setAccountC(Account accountC) {
        this.accountC = accountC;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

    //Strings to Print
    public String showContact() {
        return ("id: " + id + " -> name: " + getName());
    }

    @Override
    public String toString() {
        return "=== Contact " + id + " ===\n"+
                "- name: " + getName() + "\n"+
                "- phone number: " + getPhoneNumber() + "\n"+
                "- email: " + getEmail() + "\n"+
                "- company name: " + getCompanyName() + "\n";
    }

    public String printDecisionMaker() {
        return  " CONTACT " + id + "\n"+
                "   - name: " + getName() + "\n"+
                "   - phone number: " + getPhoneNumber() + "\n"+
                "   - email: " + getEmail() + "\n"+
                "   - company name: " + getCompanyName() + "\n";
    }

}