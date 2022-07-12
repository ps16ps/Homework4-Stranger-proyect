package com.ironhack.edgeservice.model;

import com.ironhack.edgeservice.enums.Industry;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Account {
    //Account Objects
    @Id  // indicate that id is the primary column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indicate that is auto increment column
    private long id;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private int employeeCount;
    private String city;
    private String country;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return employeeCount == account.employeeCount && industry == account.industry && city.equals(account.city) &&
                country.equals(account.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(industry, employeeCount, city, country);
    }
}
