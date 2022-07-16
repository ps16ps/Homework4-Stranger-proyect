package com.ironhack.opportunityservice.controller.dto;


import javax.validation.constraints.Min;

public class OpportunityDTO {
    private String product;
    @Min(value = 1,message = "The quantity of trucks has to be higher than 0")
    private int quantity;

    private Long decisionMakerId;

    //relation Many TO One
    private Long accountId;

    private Long salesRepId;

    public OpportunityDTO() {
    }
    public OpportunityDTO(String product, int quantity, Long decisionMakerId, Long accountId, Long salesRepId) {
        this.product = product;
        this.quantity = quantity;
        this.decisionMakerId = decisionMakerId;
        this.accountId = accountId;
        this.salesRepId = salesRepId;
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

    public Long getDecisionMakerId() {
        return decisionMakerId;
    }

    public void setDecisionMakerId(Long decisionMakerId) {
        this.decisionMakerId = decisionMakerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Long salesRepId) {
        this.salesRepId = salesRepId;
    }
}
