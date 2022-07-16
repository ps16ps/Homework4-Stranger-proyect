package com.ironhack.opportunityservice.controller.dto;

public class StatusDTO {

    private String status;

    public StatusDTO() {}
    public StatusDTO(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
