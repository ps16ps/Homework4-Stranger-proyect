package com.ironhack.edgeservice.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class ConvertDTO {
    private String product;
    @Min(value = 1,message = "The quantity of trucks has to be higher than 0")
    private int quantity;

    private Long accountId;

    private String industry;
    @Min(value = 1,message = "The number of employees has to be higher than 0")
    private int employeeCount;
    @Pattern(regexp = "^[A-Za-z ]*$", message = "The city name can not contain numbers")
    private String city;
    @Pattern(regexp = "^[A-Za-z ]*$", message = "The country name can not contain numbers")
    private String country;

    public ConvertDTO() {
    }

    public ConvertDTO(String product, int quantity, Long accountId) {
        this.product = product;
        this.quantity = quantity;
        this.accountId = accountId;
    }

    public ConvertDTO(String product, int quantity, String industry, int employeeCount, String city, String country) {
        this.product = product;
        this.quantity = quantity;
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
}
