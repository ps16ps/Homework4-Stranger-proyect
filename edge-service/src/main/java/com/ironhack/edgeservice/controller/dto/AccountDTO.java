package com.ironhack.edgeservice.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class AccountDTO {
    private String industry;
    @Min(value = 1,message = "The number of employees has to be higher than 0")
    private int employeeCount;
    @Pattern(regexp = "^[A-Za-z ]*$", message = "The city name can not contain numbers")
    private String city;
    @Pattern(regexp = "^[A-Za-z ]*$", message = "The country name can not contain numbers")
    private String country;

    public AccountDTO(String industry, int employeeCount, String city, String country) {
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
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
        AccountDTO that = (AccountDTO) o;
        return employeeCount == that.employeeCount && industry.equals(that.industry) && city.equals(that.city) && country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(industry, employeeCount, city, country);
    }
}
