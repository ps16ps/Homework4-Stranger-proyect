package com.ironhack.contactproxyservice.controller.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ContactDTO {
    @NotEmpty(message = "Entity must be provided")
    private String name;
    @Size(min = 6, message = "The phone number must contain at least 6 characters")
    private String phoneNumber;
    @NotEmpty(message = "email must be provided")
    private String email;
    @NotEmpty(message = "Company name must be provided")
    private String companyName;

    public ContactDTO(String name, String phoneNumber, String email, String companyName) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
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
}
