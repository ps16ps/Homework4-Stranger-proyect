package com.ironhack.accountservice.model;

import com.ironhack.accountservice.enums.Industry;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Account{
    //Account Objects
    @Id  // indicate that id is the primary column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indicate that is auto increment column
    private long id;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private int employeeCount;
    private String city;
    private String country;
//    @OneToMany(mappedBy = "accountC")
//    private List<Contact> contactList;
//    @OneToMany(mappedBy = "accountO",fetch = FetchType.EAGER)
//    private List<Opportunity> opportunityList;

    //Constructor

    public Account() {
    }

    public Account(Industry industry, int employeeCount, String city, String country) {
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    //getters and setters needed

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    //Add contacts and opportunities to the list
//    public void addContact(Contact contact){
//        this.contactList.add(contact);
//    }
//
//    public void addOpportunity(Opportunity opportunity){
//        this.opportunityList.add(opportunity);
//    }
//
//    //Strings to Print
//    public String showAccount() {
//        return ("id: " + id + " -> id of first opportunity: " + opportunityList.get(0).getId());
//    }
//
//    @Override
//    public String toString() {
//        return "=== Account " + id + " ===\n"+
//                "- industry: " + industry +"\n"+
//                "- employee count: " + employeeCount + "\n"+
//                "- city: " + city + "\n"+
//                "- country: " + country + "\n"+
//                "- id's of the contacts: \n" + printIdsContactList(opportunityList) +
//                "- id's of the opportunities: \n" + printIdsOpportunityList(opportunityList);
//    }
//
//    public String printIdsContactList(List<Opportunity> opportunityList){
//        String idsList = "";
//        for (Opportunity opportunity : opportunityList){
//            idsList += Long.toString(opportunity.getDecisionMaker().getId()) + "\n";
//        }
//        return idsList;
//    }
//
//    public String printIdsOpportunityList(List<Opportunity> opportunityList){
//        String idsList = "";
//        for (Opportunity opportunity : opportunityList){
//            idsList += Long.toString(opportunity.getId()) + "\n";
//        }
//        return idsList;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return employeeCount == account.employeeCount && industry == account.industry && city.equals(account.city) &&
                country.equals(account.country);
                //&& contactList.equals(account.contactList) && opportunityList.equals(account.opportunityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(industry, employeeCount, city, country);
                //, contactList, opportunityList);
    }
}
